package app.broker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import saga.dtos.BookingTicketDTO;
import saga.shared.Message;

@Component
public class Orchestrator {

	@Autowired
	private BrokerService producer;

	class Process implements ListenableFutureCallback<Message> {

		public boolean status;
		public Message result;
		public Stack<Message> doneTasks;

		public Process(Stack<Message> doneTasks) {
			this.doneTasks = doneTasks;
			this.status = true;
			this.result = null;
		}

		@Override
		public void onSuccess(Message result) {
			
			this.result = result;

			if (result.isDone()) {
				this.doneTasks.push(result);
			} else {
				this.status = false;
			}

		}

		@Override
		public void onFailure(Throwable ex) {
			this.status = false;
		}

	}

	@RabbitListener(queues = "Q1")
	public Message reply(final Message msg) {

		boolean isFailed = false; // transaction status

		if (msg.getCommand() == Message.COMMAND.BOOK_TICKET) {

			BookingTicketDTO ticketDto = (BookingTicketDTO) msg.getContent();

			/*** EXECUTE BOOKING TICKET TRANSACTION ***/
			Stack<Message> tasks = new Stack<Message>(); // to-do stack
			Stack<Message> doneTasks = new Stack<Message>(); // roll-back stack
			int totalTasks = 0;

			// define tasks
			Message task1 = new Message(ticketDto.getRoomId(), Message.COMMAND.RESERVE_SEAT, "room-route", true);
			Message task2 = new Message(ticketDto, Message.COMMAND.MAKE_PAYMENT, "account-route", true);

			// push tasks to to-do stack
			tasks.push(task1);
			tasks.push(task2);

			totalTasks = tasks.size();

			// parallel processes pool - any task in this process failed will result in a
			// transaction rollback
			List<Process> processes = new ArrayList<Process>();

			// start transaction execution thread
			while (doneTasks.size() != totalTasks) {

				if (tasks.isEmpty()) {

					if (processes.size() > 0) {
						
						// continuously tracking asynchronous processes status, stop until no processes
						// left to track
						
						Iterator<Process> iterator = processes.iterator();
						while (iterator.hasNext()) {
							Process process = iterator.next();
							
							if (process.result != null || !process.status) {
								
								if (!process.status) {
									 isFailed = true;
								}
								
								iterator.remove();
								
							}
							
						}

						continue;

					}
					// all processes has been done

					// check transaction status, roll-back if failed
					if (isFailed) {

						while (!doneTasks.isEmpty()) {
							Message rollbackTask = doneTasks.pop();
							rollbackTask.setRollbackCommand(rollbackTask.getCommand());
							producer.sendAsync(rollbackTask);
						}

						break;
					}

				} else {

					Message task = tasks.pop();

					if (task.isAsync()) {

						Process process = new Process(doneTasks);

						processes.add(process);

						producer.sendAsync(task).addCallback(process);

					} else {

						Message result = producer.sendSync(task);

						if (result.isDone()) {
							doneTasks.push(result);
						} else {
							isFailed = true;
							break;
						}

					}
					// all tasks have been sent

				}

			}

		}

		// post transaction
		if (!isFailed)
			msg.setDone(true);

		return msg;

	}

}

package app.broker;

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
		
		public Process() {
			this.status = true;
		}

		@Override
		public void onSuccess(Message result) {
			this.result = result;
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
			Message task2 = new Message(null, Message.COMMAND.MAKE_PAYMENT, "account-route", true);

			// push tasks to to-do stack
			tasks.push(task1);
			tasks.push(task2);

			totalTasks = tasks.size();

			// start transaction execution thread
			while (doneTasks.size() != totalTasks) {

				Message task = tasks.pop();

				if (task.isAsync()) {
					
					Message taskDone = null;
					
					producer.sendAsync(task).addCallback(new ListenableFutureCallback<Message>() {

						@Override
						public void onSuccess(Message result) {
							taskDone = result;
							doneTasks.push(result);
						}

						@Override
						public void onFailure(Throwable ex) {
							isFailed = true;
						}

					});
					
					
					
				} else {

					Message result = producer.sendSync(task);

					if (result.isDone()) {
						doneTasks.push(result);
					} else {
						isFailed = true;
						break;
					}

				}
				// transaction over

				// check transaction status, roll back if failed
				if (isFailed) {

					while (!doneTasks.isEmpty()) {
						Message rollbackTask = doneTasks.pop();
						rollbackTask.setRollbackCommand(rollbackTask.getCommand());
						
						producer.sendAsync(rollbackTask).addCallback(new ListenableFutureCallback<Message>() {

							@Override
							public void onSuccess(Message result) {
								System.out.println("Rollback success");
							}

							@Override
							public void onFailure(Throwable ex) {
								// ???
							}

						});
					}
					
				}

			}

		}

		// post transaction
		if (!isFailed)
			msg.setDone(true);

		return msg;

	}

}

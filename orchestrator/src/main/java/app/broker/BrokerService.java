package app.broker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate.RabbitConverterFuture;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import saga.core.Message;

@Component
public class BrokerService {

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private AsyncRabbitTemplate asyncTpl;

	@Autowired
	private DirectExchange direct;

	@Scheduled(fixedDelay = 1000, initialDelay = 500)
	public Message sendSync(Message message) {

		String route = message.getRoute();

		return (Message) template.convertSendAndReceive(direct.getName(), route, message);

	}

	@Scheduled(fixedDelay = 1000L)
	public RabbitConverterFuture<Message> sendAsync(Message message) {

		String route = message.getRoute();

		return asyncTpl.convertSendAndReceive(direct.getName(), route, message);

	}

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

	public void tracker(Stack<Message> tasks, Message msg) {

		boolean isFailed = false; // transaction status

		/*** EXECUTE BOOKING TICKET TRANSACTION ***/
		Stack<Message> doneTasks = new Stack<Message>(); // roll-back stack
		int totalTasks = 0;

		totalTasks = tasks.size();

		List<Process> processes = new ArrayList<Process>();

		// start transaction execution thread
		while (doneTasks.size() != totalTasks) {

			if (tasks.isEmpty()) {

				if (processes.size() > 0) {

					// continuously tracking asynchronous processes status, stop until no processes,
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

				// sending command to other services
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
		if (!isFailed)
			msg.setDone(true);

		if (isFailed) {

			while (!doneTasks.isEmpty()) {
				Message rollbackTask = doneTasks.pop();
				rollbackTask.setRollbackCommand(rollbackTask.getCommand());
				producer.sendAsync(rollbackTask);
			}

		}

	}

}

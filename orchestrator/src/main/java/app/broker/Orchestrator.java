package app.broker;

import java.util.Stack;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import saga.shared.Message;


@Component
public class Orchestrator {

	@Autowired
	private BrokerService producer;

	@RabbitListener(queues = "Q1")
	public Message reply(final Message msg) {
		
		Stack<Message> tasks = new Stack<Message>();
		
		Message task

		Message taskA = new Message("orchestrator", "A", "AAA", "A");
		Message taskB = new Message("orchestrator", "B", "BBB", "B");

//		Message result = producer.sendSync(taskA);
//		Message result = producer.sendSync(taskB);
//		System.out.println(result);
		
		producer.sendAsync(taskA).addCallback(new ListenableFutureCallback<Message>() {

			@Override
			public void onSuccess(Message result) {
				System.out.println(result);
			}

			@Override
			public void onFailure(Throwable ex) {
				// TODO Auto-generated method stub
				
			}
		});
		
		producer.sendAsync(taskB).addCallback(new ListenableFutureCallback<Message>() {

			@Override
			public void onSuccess(Message result) {
				System.out.println(result);
			}

			@Override
			public void onFailure(Throwable ex) {
				// TODO Auto-generated method stub
				
			}
		});

		return new Message("orchestrator", "main.service", "jobs finished.");

	}

	

}

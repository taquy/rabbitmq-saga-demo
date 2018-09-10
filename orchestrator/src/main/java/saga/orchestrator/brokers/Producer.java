package saga.orchestrator.brokers;

import java.util.Stack;

import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate.RabbitConverterFuture;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import messages.Message;
import saga.orchestrator.config.ProducerMainConfig;
import saga.orchestrator.config.ProducerSubConfig;

@Component
public class Producer {

	@Autowired
	private AsyncRabbitTemplate asyncTpl;

	@Autowired
	private RabbitTemplate tpl;

	@RabbitListener(queues = ProducerMainConfig.qn)
	public Message reply(final Message msg) {

		System.out.println(msg);

		// defining transaction
//		Stack<Message> futureJobs = new Stack<Message>();
//		Stack<Message> doneJobs = new Stack<Message>();
//		
//		futureJobs.add(new Message("orchestrator", "service.A", "execute task at A", ProducerSubConfig.rka));
//		futureJobs.add(new Message("orchestrator", "service.B", "execute task at B", ProducerSubConfig.rkb));
		
		Message task1 = new Message("orchestrator", "service.A", "execute task at A", ProducerSubConfig.rka, false);
		Message task2 = new Message("orchestrator", "service.B", "execute task at B", ProducerSubConfig.rkb, false);
		this.execute(task1);
		this.execute(task2);
		
		return new Message("orchestrator", "main.service", "jobs done", ProducerMainConfig.rk);
	}
	
	private Message execute(Message task) {
		
		if (task.isAsync()) {
			asyncCommand(task).addCallback(new ListenableFutureCallback<Message>() {

				@Override
				public void onSuccess(Message result) {
					System.out.println("Async");
					System.out.println(result);
				}

				@Override
				public void onFailure(Throwable ex) {
					System.out.println("failed");
				}
				
			});
		} else {
			Message result = this.syncCommand(task);
			System.out.println("Sync");
			System.out.println(result);
		}
		
		return null;
	}
		
	@Scheduled(fixedDelay = 1000L)
	private Message syncCommand(Message msg) {
		String en = ProducerSubConfig.en;
		String rk = msg.getRoute();
		
		System.out.println(rk);
		
		return (Message) tpl.convertSendAndReceive(en, rk, msg);
	}

	@Scheduled(fixedDelay = 1000L)
	private RabbitConverterFuture<Message> asyncCommand(Message msg) {

		String en = ProducerSubConfig.en;
		String rk = msg.getRoute();
		
		return asyncTpl.convertSendAndReceive(en, rk, msg);
	}
	
}

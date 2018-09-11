package saga.service.a.broker;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import saga.shared.Message;

@Service
public class Receiver {

	@RabbitListener(queues = "#{queueA.name}")
	
	public Message executor(Message msg) throws InterruptedException {

		System.out.println(msg);
		
		Thread.sleep(4000);

		return new Message(msg.getTo(), msg.getFrom(), "A executed task", msg.getRoute());
		
	}

}

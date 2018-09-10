package saga.service.b.broker;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import messages.Message;

@Service
public class Broker {
	
	@RabbitListener(queues = "q1s.rq")
	public Message executor(Message msg) {
		
		System.out.println(msg);
		
		return new Message(msg.getTo(), msg.getFrom(), "A executed task", msg.getRoute());
	}
	
}

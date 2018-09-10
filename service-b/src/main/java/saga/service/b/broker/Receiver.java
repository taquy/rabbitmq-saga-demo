package saga.service.b.broker;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import messages.Message;

@Service
public class Receiver {

	@RabbitListener(queues = "#{queueB.name}")
	public Message executor(Message msg) {

		System.out.println(msg);

		return new Message(msg.getTo(), msg.getFrom(), "B executed task", msg.getRoute());
	}

}

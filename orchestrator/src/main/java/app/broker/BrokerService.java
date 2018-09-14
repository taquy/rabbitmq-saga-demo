package app.broker;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate.RabbitConverterFuture;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import saga.core.Message;

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
}

package saga.orchestrator.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ProducerSubConfig {

	public static final String en = "e1s";
	public static final String qnrq = "q1s.rq";
	public static final String qnrp = "q1s.rp";
	public static final String rka = "rk1sa";
	public static final String rkb = "rk1sb";

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private ConnectionFactory connectionFactory;

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(en);
	}

	// queue for requests
	@Bean
	Queue queueRq() {
		return QueueBuilder.durable(qnrq).build();
	}

	// queue for responses
	@Bean
	Queue queueRp() {
		return QueueBuilder.durable(qnrp).build();
	}
	
	// bind queue for service A
	@Bean
	Binding bindA() {
		return BindingBuilder.bind(queueRq()).to(exchange()).with(rka);
	}
	
	// bind queue for service B
	@Bean
	Binding bindB() {
		return BindingBuilder.bind(queueRq()).to(exchange()).with(rkb);
	}
	
	// async rabbit template
	@Bean
	AsyncRabbitTemplate template() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueueNames(qnrp);
		return new AsyncRabbitTemplate(rabbitTemplate, container);
	}
	
	@Bean
	public Jackson2JsonMessageConverter jsonConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
}

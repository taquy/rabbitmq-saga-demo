package app.broker;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {
	
	@Bean
	public DirectExchange direct() {
		return new DirectExchange("sub-services-router");
	}

	@Bean
	public Queue queueA() {
		return new AnonymousQueue();
	}

	@Bean
	public Binding bindingA(DirectExchange direct, Queue queueA) {
		return BindingBuilder.bind(queueA).to(direct).with("room-route");
	}
	
	@Bean
	public Receiver receiver() {
		return new Receiver();
	}
	
	@Bean
	public Jackson2JsonMessageConverter jsonConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
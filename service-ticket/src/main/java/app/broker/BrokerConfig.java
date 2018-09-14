package app.broker;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {
	
	/**** QUEUE CONFIGURATION ***/
	public static final String q1 = "Q1"; 	// queue name
	public static final String e1 = "E1"; 	// exchange name
	public static final String r1 = "R1"; // route key
	
	@Bean
	Queue queue() {
		return QueueBuilder.durable(q1).build();
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(e1);
	}

	@Bean
	Binding binding(DirectExchange exchange) {
		return BindingBuilder.bind(queue()).to(exchange).with(r1);
	}
	
	
	@Autowired
	ConnectionFactory factory;
	
	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate tpl = new RabbitTemplate(factory);
		tpl.setMessageConverter(jsonConverter());
		tpl.setReplyTimeout(5000L);
		return tpl;
	}
	
	@Bean
	public Jackson2JsonMessageConverter jsonConverter() {
		return new Jackson2JsonMessageConverter();
	}

}

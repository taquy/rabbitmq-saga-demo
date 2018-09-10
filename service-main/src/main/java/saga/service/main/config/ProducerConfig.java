package saga.service.main.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ProducerConfig {
	
	/**** QUEUE CONFIGURATION ***/
	public static final String qn = "q1"; 	// queue name
	public static final String rk = "r1"; // route key
	public static final String en = "e1"; 	// exchange name
	
	@Bean
	Queue queue() {
		return new Queue(qn, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(en);
	}

	@Bean
	Binding binding(DirectExchange exchange, Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with(rk);
	}
	
	@Bean
	public Jackson2JsonMessageConverter jsonConverter() {
		return new Jackson2JsonMessageConverter();
	}

}

package app;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import app.broker.BrokerConfig;
import saga.shared.Message;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	// controller
	@Autowired
	private RabbitTemplate tpl;

	@GetMapping
	public String hi() {
		String rk = BrokerConfig.r1;
		String en = BrokerConfig.e1;
		
		Message msg = new Message("main.service", "orchestrator", "do transaction A-B", rk);
		Message rsl = (Message) tpl.convertSendAndReceive(en, rk, msg);
		
		return rsl.getMessage();
	}
	
}

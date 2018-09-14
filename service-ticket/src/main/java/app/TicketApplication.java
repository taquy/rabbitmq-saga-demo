package app;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.broker.BrokerConfig;
import saga.dtos.BookingTicketDTO;
import saga.shared.Message;

@SpringBootApplication
@RestController
public class TicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketApplication.class, args);
	}
	
	// controller
	@Autowired
	private RabbitTemplate tpl;

	@GetMapping
	public String bookingTicket(@RequestParam("user_id") int userId, @RequestParam("room_id") int roomId) {
		String rk = BrokerConfig.r1; // route key
		String en = BrokerConfig.e1; // router name
		
		// default ticket cost for demo
		double ticketCost = 10;
		
		// construct message
		BookingTicketDTO dto = new BookingTicketDTO(userId, roomId, ticketCost);
		int cmd = Message.COMMAND.BOOK_TICKET;
		Message msg = new Message(dto, cmd, rk);
		
		// send request - initiate transaction
		Message rsl = (Message) tpl.convertSendAndReceive(en, rk, msg);
		
		if (rsl.isDone()) return "success";
		return "failed";
	}
	
}

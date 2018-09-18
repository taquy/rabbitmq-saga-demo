package app;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import app.broker.BrokerConfig;
import app.entities.Ticket;
import app.repositories.TicketRepository;
import saga.core.Message;
import saga.dtos.BookingTicketDTO;
import saga.util.SagaConverter;

@SpringBootApplication
@RestController
public class TicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketApplication.class, args);
	}

	// controller
	@Autowired
	private RabbitTemplate tpl;

	@Autowired
	private TicketRepository ticketRepo;

	@PostMapping
	public String bookingTicket(@RequestParam("user_id") int userId, @RequestParam("room_id") int roomId) {
		String rk = BrokerConfig.r1; // route key
		String en = BrokerConfig.e1; // router name

		// default ticket cost for demo
		double ticketCost = 10;

		// construct message
		BookingTicketDTO dto = new BookingTicketDTO(userId, roomId, ticketCost);
		String content = null;

		try {
			content = SagaConverter.encode(dto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "failed";
		}

		Message msg = new Message(content, Message.COMMAND.BOOK_TICKET, rk);
		Message result = (Message) tpl.convertSendAndReceive(en, rk, msg);

		if (result == null || !result.isDone())
			return "failed";

		// process other logic if necessary
		Ticket ticket = new Ticket(userId, roomId);
		ticketRepo.save(ticket);

		return "success";

	}

}

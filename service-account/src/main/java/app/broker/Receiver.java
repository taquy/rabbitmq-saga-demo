package app.broker;

import java.io.IOException;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.exceptions.InsufficientBudgetException;
import app.services.BudgetService;
import saga.core.Message;
import saga.dtos.BookingTicketDTO;
import saga.util.SagaConverter;

@Service
public class Receiver {

	@Autowired
	private BudgetService budgetService;

	@RabbitListener(queues = "#{queueB.name}")
	public Message executor(Message msg) throws IOException {

		if (msg.getCommand() == Message.COMMAND.MAKE_PAYMENT) {
			
			System.out.println("Received command: " + msg.getId());

			BookingTicketDTO ticketDto = SagaConverter.decode(msg.getContent(), BookingTicketDTO.class);

			try {
				budgetService.withdrawn(ticketDto.getUserId(), ticketDto.getTicketCost());
				msg.setDone(true);
			} catch (AccountNotFoundException | InsufficientBudgetException e) {
				e.printStackTrace();
			}
		}

		return msg;

	}

}

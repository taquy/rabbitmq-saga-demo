package app.broker;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.exceptions.InsufficientBudgetException;
import app.services.BudgetService;
import saga.dtos.BookingTicketDTO;
import saga.shared.Message;

@Service
public class Receiver {

	@Autowired
	private BudgetService budgetService;

	@RabbitListener(queues = "#{queueB.name}")
	public Message executor(Message msg) {

		if (msg.getCommand() == Message.COMMAND.MAKE_PAYMENT) {
			
			System.out.println("Received command: " + msg.getId());

			BookingTicketDTO ticketDto = (BookingTicketDTO) msg.getContent();

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

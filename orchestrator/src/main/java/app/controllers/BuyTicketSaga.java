package app.controllers;

import java.io.IOException;
import java.util.Stack;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.broker.BrokerService;
import saga.core.Message;

@Component
public class BuyTicketSaga {
	
	@Autowired
	private BrokerService brokerService;
	
	@RabbitListener(queues = "Q1")
	public Message reply(Message msg) throws IOException {
		
		// command must matched, if not, return false message (default)
		if (msg.getCommand() != Message.COMMAND.BOOK_TICKET) return msg;

		/*** DEFINE TRANSACTION***/
		Stack<Message> tasks = new Stack<Message>(); // to-do stack
		Message[] tasksPrep = new Message[] {
			new Message(msg.getContent(), Message.COMMAND.RESERVE_SEAT, "room-route"),
			new Message(msg.getContent(), Message.COMMAND.MAKE_PAYMENT, "account-route")
		};

		// push tasks to to-do stack
		for (Message task : tasksPrep) {
			tasks.push(task);
		}
		
		/*** EXECUTE TRANSACTION ***/
		brokerService.tracker(tasks, msg);

		return msg;

	}
}

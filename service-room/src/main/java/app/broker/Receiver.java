package app.broker;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.exceptions.InsufficientSeatsException;
import app.exceptions.RoomNotFoundException;
import app.services.RoomService;
import saga.core.Message;
import saga.core.SagaConverter;
import saga.dtos.BookingTicketDTO;

@Service
public class Receiver {

	@Autowired
	private RoomService roomService;

	@RabbitListener(queues = "#{queueA.name}")
	public Message executor(Message msg) throws IOException {

		if (msg.getCommand() == Message.COMMAND.RESERVE_SEAT) {
			
			
			BookingTicketDTO ticketDto = SagaConverter.decode(msg.getContent(), BookingTicketDTO.class);
			
			try {
				roomService.reserveSeat(ticketDto.getRoomId());
				msg.setDone(true);
			} catch (RoomNotFoundException | InsufficientSeatsException e) {
				e.printStackTrace();
			}
			
		} else if (msg.getCommand() == Message.COMMAND.RESERVE_SEAT_ROLLBACK) {
			
		}

		return msg;
	}

}

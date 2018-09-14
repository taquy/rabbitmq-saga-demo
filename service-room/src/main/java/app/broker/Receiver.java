package app.broker;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.exceptions.InsufficientSeatsException;
import app.exceptions.RoomNotFoundException;
import app.services.RoomService;
import saga.shared.Message;

@Service
public class Receiver {

	@Autowired
	private RoomService roomService;

	@RabbitListener(queues = "#{queueA.name}")
	public Message executor(Message msg) {

		if (msg.getCommand() == Message.COMMAND.RESERVE_SEAT) {
			Integer roomId = (Integer) msg.getContent();

			try {
				roomService.reserveSeat(roomId);
				msg.setDone(true);
			} catch (RoomNotFoundException | InsufficientSeatsException e) {
				e.printStackTrace();
			}
		}

		return msg;
	}

}

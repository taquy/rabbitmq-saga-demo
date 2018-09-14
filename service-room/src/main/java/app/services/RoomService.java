package app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entities.Room;
import app.exceptions.InsufficientSeatsException;
import app.exceptions.RoomNotFoundException;
import app.repositories.RoomRepository;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepo;

	public boolean hasEnoughSeats(int roomId, int seats) throws RoomNotFoundException {

		if (!roomRepo.existsById(roomId))
			throw new RoomNotFoundException();

		Optional<Room> room = roomRepo.findById(roomId);

		if (!room.isPresent())
			throw new RoomNotFoundException();

		return room.get().getSeatsAvailable() >= seats;
	}

	public boolean hasSeat(int roomId) throws RoomNotFoundException {
		return hasEnoughSeats(roomId, 1);
	}

	public void reserveSeat(int roomId) throws RoomNotFoundException, InsufficientSeatsException {

		if (!hasSeat(roomId))
			throw new InsufficientSeatsException();

		// reserve seat here
		Room room = roomRepo.findById(roomId).get();

		int subtraction = room.getSeatsAvailable() - 1;

		room.setSeatsAvailable(subtraction);

		roomRepo.save(room);

	}
}

package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import app.entities.Room;
import app.repositories.RoomRepository;

@SpringBootApplication
public class RoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomApplication.class, args);
	}
	
	@Component
	public class CommandLineAppStartupRunner implements CommandLineRunner {

		@Autowired
		RoomRepository roomRepo;
		
		@Override
		public void run(String... args) throws Exception {
			
			// create dummy data: room with 5 seats
			Room room = new Room(5);
			roomRepo.save(room);
			
		}
	}
}

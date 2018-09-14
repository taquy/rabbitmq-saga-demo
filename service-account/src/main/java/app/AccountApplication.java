package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import app.entities.Account;
import app.repositories.AccountRepository;

@SpringBootApplication
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@Component
	public class CommandLineAppStartupRunner implements CommandLineRunner {

		@Autowired
		AccountRepository accountRepo;
		
		@Override
		public void run(String... args) throws Exception {
			
			// create dummy data
			Account acc1 = new Account(1, 100);
			Account acc2 = new Account(2, 10);
			accountRepo.save(acc1);
			accountRepo.save(acc2);
			
		}
	}
}

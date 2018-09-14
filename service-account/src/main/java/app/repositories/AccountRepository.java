package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	Account findByUserId(int userId);
	boolean existsByUserId(int userId);
	
}

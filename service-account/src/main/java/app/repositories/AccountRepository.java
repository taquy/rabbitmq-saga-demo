package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.entities.Account;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	Account findByUserId(int userId);
	boolean existsByUserId(int userId);
	
}

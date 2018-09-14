package app.services;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entities.Account;
import app.exceptions.InsufficientBudgetException;
import app.repositories.AccountRepository;

@Service
public class BudgetService {

	@Autowired
	AccountRepository accountRepo;

	public boolean hasEnoughBudget(int userId, double cost) throws AccountNotFoundException {

		if (!accountRepo.existsByUserId(userId))
			throw new AccountNotFoundException();

		Account account = accountRepo.findByUserId(userId);

		return account.getDeposit() > cost;
	}

	public void withdrawn(int userId, double amount) throws AccountNotFoundException, InsufficientBudgetException {

		if (!hasEnoughBudget(userId, amount))
			throw new InsufficientBudgetException();
		
		// make payment here
		Account account = accountRepo.findByUserId(userId);
		
		double subtraction = account.getDeposit() - amount;
		
		account.setDeposit(subtraction);
		
		accountRepo.save(account);

	}

}

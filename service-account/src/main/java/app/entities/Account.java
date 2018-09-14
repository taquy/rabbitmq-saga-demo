package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private int accountId;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "deposit")
	private double deposit = 0;

	public Account() {
	}

	public Account(int user_id, int deposit) {
		this.userId = user_id;
		this.deposit = deposit;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double subtraction) {
		this.deposit = subtraction;
	}

}

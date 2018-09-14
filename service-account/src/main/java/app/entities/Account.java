package app.entities;

import javax.persistence.Entity;

@Entity
public class Account {

	private int userId;
	private int deposit;

	public Account() {
	}

	public Account(int user_id, int deposit) {
		this.userId = user_id;
		this.deposit = deposit;
	}

	public int getUser_id() {
		return userId;
	}

	public void setUser_id(int user_id) {
		this.userId = user_id;
	}

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

}

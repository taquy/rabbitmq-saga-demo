package app.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private int ticketId;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "room_id")
	private int roomId;

	@Column(name = "issued_date")
	private Date issuedDate;

	public Ticket() {
	}

	public Ticket(int userId, int roomId) {
		this.userId = userId;
		this.roomId = roomId;
		this.issuedDate = new Date();
	}

	public int getId() {
		return ticketId;
	}

	public void setId(int ticketId) {
		this.ticketId = ticketId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public Date getBookedDate() {
		return issuedDate;
	}

	public void setBookedDate(Date bookedDate) {
		this.issuedDate = bookedDate;
	}

}

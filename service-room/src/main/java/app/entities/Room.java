package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private int roomId;
	
	@Column(name = "seats_available")
	private int seatsAvailable;
	
	@Column(name = "price")
	private double price;

	public Room() {
	}

	public Room(int seatsAvailable, double price) {
		this.seatsAvailable = seatsAvailable;
		this.price = price;
	}

	public int getId() {
		return roomId;
	}

	public void setId(int roomId) {
		this.roomId = roomId;
	}

	public int getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}

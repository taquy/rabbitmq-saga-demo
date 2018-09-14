package saga.dtos;

public class BookingTicketDTO {
	private int userId;
	private int roomId;
	private double ticketCost;

	public BookingTicketDTO() {
	}

	public BookingTicketDTO(int userId, int roomId, double ticketCost) {
		this.userId = userId;
		this.roomId = roomId;
		this.ticketCost = ticketCost;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public double getTicketCost() {
		return ticketCost;
	}

	public void setTicketCost(double ticketCost) {
		this.ticketCost = ticketCost;
	}

}

package saga.dtos;

public class BookingTicketDTO {
	private int userId;
	private int roomId;

	public BookingTicketDTO() {
	}
	
	public BookingTicketDTO(int userId, int roomId) {
		this.userId = userId;
		this.roomId = roomId;
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

}

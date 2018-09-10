package messages;

public final class Message {
	private long id;
	private String from;
	private String to;
	private String message;
	private String route;
	private boolean status;
	private boolean isAsync;

	public Message(String from, String to, String message, String route) {
		this.from = from;
		this.to = to;
		this.message = message;
		this.route = route;
		this.status = false;
		this.isAsync = false;
	}
	
	public Message(String from, String to, String message, String route, boolean isAsync) {
		this.from = from;
		this.to = to;
		this.message = message;
		this.route = route;
		this.status = false;
		this.isAsync = isAsync;
	}

	public Message() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}
	
	public boolean isAsync() {
		return isAsync;
	}

	public void setAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}

	@Override
	public String toString() {
		return "Message [from=" + from + ", to=" + to + ", message=" + message + ", route=" + route + ", status="
				+ status + "]";
	}
}

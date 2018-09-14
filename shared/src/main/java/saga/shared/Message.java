package saga.shared;

import java.util.UUID;

public final class Message {
	private String id;
	private Object content;
	private int command;
	private String route;
	private boolean isAsync;
	private boolean isDone;

	public static final class COMMAND {
		// margin of commands: to identify safe gaps between command and its rollback command
		public static final int MARGIN = 1000;
		
		public static final int BOOK_TICKET = MARGIN + 0;
		public static final int RESERVE_SEAT = MARGIN + 1;
		public static final int MAKE_PAYMENT = MARGIN + 2;
		
		public static final int BOOK_TICKET_ROLLBACK = BOOK_TICKET + MARGIN;
		public static final int RESERVE_SEAT_ROLLBACK = RESERVE_SEAT + MARGIN;
		public static final int MAKE_PAYMENT_ROLLBACK = MAKE_PAYMENT + MARGIN;
	}

	public Message(Object content, int command, String route) {
		this.id = genId();

		this.content = content;
		this.command = command;
		this.route = route;
		
		this.isAsync = false;
		this.isDone = false;
	}
	
	public Message(Object content, int command, String route, boolean isAsync) {
		this.id = genId();

		this.content = content;
		this.command = command;
		this.route = route;
		this.isAsync = isAsync;

		this.isDone = false;

	}

	public String genId() {
		return UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public boolean isAsync() {
		return isAsync;
	}

	public void setAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}
	
	public void setRollbackCommand(int command) {
		this.command = command + COMMAND.MARGIN;
	}

}
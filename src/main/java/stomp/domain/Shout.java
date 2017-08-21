package stomp.domain;

public class Shout {
	private String message;

	public Shout(String message) {
		this.message = message;
	}

	public Shout() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

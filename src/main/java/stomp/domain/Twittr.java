package stomp.domain;

import java.util.Date;

public class Twittr {
	private Long id;
	private String user;
	private String message;
	private Date timestamp;
	
	public Twittr(String user, String message, Date timestamp) {
		super();
		this.user = user;
		this.message = message;
		this.timestamp = timestamp;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}

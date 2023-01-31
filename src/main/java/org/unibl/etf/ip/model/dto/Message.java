package org.unibl.etf.ip.model.dto;

public class Message {

	private Integer id;

	private String title;

	private String content;
	
	private boolean read;

	private String userEmail;

	public Message() {
	}

	public Message(Integer id, String title, String content, boolean read, String userEmail) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.read = read;
		this.userEmail = userEmail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}

package org.example.server.bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

	private final static String BROADCAST = "BROADCAST";
	private String sender;
	private String receiver;
	private String message;
	private LocalDateTime timestamp = LocalDateTime.now(); // horodatage

	public Message() {
		this.sender = "";
		this.receiver = BROADCAST;
		this.message = "";
	}

	public Message(String sender, String message) {
		this.sender = sender;
		this.receiver = BROADCAST;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

	public Message(String sender, String receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		return "[" + timestamp.format(formatter) + "] " + sender + " : " + message;
	}
}

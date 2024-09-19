package com.mb.helpers;

import lombok.Builder;

@Builder
public class Message {

	private String content;
	@Builder.Default
	private MessageType type = MessageType.blue;

	public Message() {
		super();
	}

	public Message(String content, MessageType type) {
		super();
		this.content = content;
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Message [content=" + content + "]";
	}

}

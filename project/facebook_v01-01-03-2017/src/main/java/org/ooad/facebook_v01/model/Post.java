package org.ooad.facebook_v01.model;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Post {
	private String postText;
	private int senderId;
	private int receiverId;
	
	public Post(){
		
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
}

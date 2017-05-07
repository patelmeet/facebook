package org.ooad.facebook_v01.model;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Post {
	private String postText;
	private int senderId;
	private int receiverId;
	private String post_img_url;
	private int eventId;
	
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

	public String getPost_img_url() {
		return post_img_url;
	}

	public void setPost_img_url(String post_img_url) {
		this.post_img_url = post_img_url;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
}

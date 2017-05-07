package org.ooad.facebook_v01.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Friend {
	
	private int friend_pk;
	private String friend_name;
	private int total_friends;
	private String friend_picture;
	
	public Friend(){
		
	}
	
	public Friend(int friend_pk, String friend_name, int total_friends, String friend_picture) {
		this.friend_pk = friend_pk;
		this.friend_name = friend_name;
		this.total_friends = total_friends;
		this.friend_picture = friend_picture;
	}

	public int getFriend_pk() {
		return friend_pk;
	}
	public void setFriend_pk(int friend_pk) {
		this.friend_pk = friend_pk;
	}
	public String getFriend_name() {
		return friend_name;
	}
	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}
	public int getTotal_friends() {
		return total_friends;
	}
	public void setTotal_friends(int total_friends) {
		this.total_friends = total_friends;
	}
	public String getFriend_picture() {
		return friend_picture;
	}
	public void setFriend_picture(String friend_picture) {
		this.friend_picture = friend_picture;
	}
	
	
}

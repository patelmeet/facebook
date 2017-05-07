package org.ooad.facebook_v01.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GroupJoinRequestList {
	
	private int userDetails_pk;
	private String userDetails_userName;
	private String userDetails_firstName;
	private String userDetails_lastName;
	private String userdetails_picurl;
	
	public GroupJoinRequestList() {
	}

	public int getUserDetails_pk() {
		return userDetails_pk;
	}

	public void setUserDetails_pk(int userDetails_pk) {
		this.userDetails_pk = userDetails_pk;
	}

	public String getUserDetails_userName() {
		return userDetails_userName;
	}

	public void setUserDetails_userName(String userDetails_userName) {
		this.userDetails_userName = userDetails_userName;
	}

	public String getUserDetails_firstName() {
		return userDetails_firstName;
	}

	public void setUserDetails_firstName(String userDetails_firstName) {
		this.userDetails_firstName = userDetails_firstName;
	}

	public String getUserDetails_lastName() {
		return userDetails_lastName;
	}

	public void setUserDetails_lastName(String userDetails_lastName) {
		this.userDetails_lastName = userDetails_lastName;
	}

	public String getUserdetails_picurl() {
		return userdetails_picurl;
	}

	public void setUserdetails_picurl(String userdetails_picurl) {
		this.userdetails_picurl = userdetails_picurl;
	}
	
	

}

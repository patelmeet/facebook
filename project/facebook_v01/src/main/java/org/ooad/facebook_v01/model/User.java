package org.ooad.facebook_v01.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private String userDetails_pk;
	private String userDetails_userName;
	private String userDetails_firstName;
	private String userDetails_lastName;
	private String userDetails_mobile;
	private String userDetails_email;
	private String userDetails_password;
	private String userDetails_day;
	private String userDetails_month;
	private String userDetails_year;
	private char userDetails_gender;
	private String userdetails_picurl;
	private String userdetails_coverpicurl;
	
	public User(){
		
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
	public String getUserDetails_password() {
		return userDetails_password;
	}
	public void setUserDetails_password(String userDetails_password) {
		this.userDetails_password = userDetails_password;
	}
	public char getUserDetails_gender() {
		return userDetails_gender;
	}
	public void setUserDetails_gender(char userDetails_gender) {
		this.userDetails_gender = userDetails_gender;
	}

	public String getUserDetails_day() {
		return userDetails_day;
	}

	public void setUserDetails_day(String userDetails_day) {
		this.userDetails_day = userDetails_day;
	}

	public String getUserDetails_month() {
		return userDetails_month;
	}

	public void setUserDetails_month(String userDetails_month) {
		this.userDetails_month = userDetails_month;
	}

	public String getUserDetails_year() {
		return userDetails_year;
	}

	public void setUserDetails_year(String userDetails_year) {
		this.userDetails_year = userDetails_year;
	}

	public String getUserDetails_mobile() {
		return userDetails_mobile;
	}

	public void setUserDetails_mobile(String userDetails_mobile) {
		this.userDetails_mobile = userDetails_mobile;
	}

	public String getUserDetails_email() {
		return userDetails_email;
	}

	public void setUserDetails_email(String userDetails_email) {
		this.userDetails_email = userDetails_email;
	}

	public String getUserDetails_pk() {
		return userDetails_pk;
	}

	public void setUserDetails_pk(String userDetails_pk) {
		this.userDetails_pk = userDetails_pk;
	}

	public String getUserdetails_picurl() {
		return userdetails_picurl;
	}

	public void setUserdetails_picurl(String userdetails_picurl) {
		this.userdetails_picurl = userdetails_picurl;
	}

	public String getUserdetails_coverpicurl() {
		return userdetails_coverpicurl;
	}

	public void setUserdetails_coverpicurl(String userdetails_coverpicurl) {
		this.userdetails_coverpicurl = userdetails_coverpicurl;
	}

}

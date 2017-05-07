package org.ooad.facebook_v01.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContactAndBasicInfo {
	private String userdetails_mobile;
	private String userdetails_email;
	private String contact_and_basic_info_user_address;
	private String userdetails_dob;
	private char userdetails_gender;
	private String religions_religion_name;
	private String contact_and_basic_info_religious_view;
	private char contact_and_basic_info_interested_in;
	
	public ContactAndBasicInfo(){
		
	}
	
	public ContactAndBasicInfo(String userdetails_mobile, String userdetails_email,
			String contact_and_basic_info_user_address,
			String userdetails_dob, char userdetails_gender,
			String religions_religion_name,
			String contact_and_basic_info_religious_view, char contact_and_basic_info_interested_in) {
		super();
		this.userdetails_mobile = userdetails_mobile;
		this.userdetails_email = userdetails_email;
		this.contact_and_basic_info_user_address = contact_and_basic_info_user_address;
		this.userdetails_dob = userdetails_dob;
		this.userdetails_gender = userdetails_gender;
		this.religions_religion_name = religions_religion_name;
		this.contact_and_basic_info_religious_view = contact_and_basic_info_religious_view;
		this.contact_and_basic_info_interested_in = contact_and_basic_info_interested_in;
	}

	public String getUserdetails_mobile() {
		return userdetails_mobile;
	}

	public void setUserdetails_mobile(String userdetails_mobile) {
		this.userdetails_mobile = userdetails_mobile;
	}

	public String getUserdetails_email() {
		return userdetails_email;
	}

	public void setUserdetails_email(String userdetails_email) {
		this.userdetails_email = userdetails_email;
	}

	public String getContact_and_basic_info_user_address() {
		return contact_and_basic_info_user_address;
	}

	public void setContact_and_basic_info_user_address(String contact_and_basic_info_user_address) {
		this.contact_and_basic_info_user_address = contact_and_basic_info_user_address;
	}

	public String getUserdetails_dob() {
		return userdetails_dob;
	}

	public void setUserdetails_dob(String userdetails_dob) {
		this.userdetails_dob = userdetails_dob;
	}

	public char getUserdetails_gender() {
		return userdetails_gender;
	}

	public void setUserdetails_gender(char userdetails_gender) {
		this.userdetails_gender = userdetails_gender;
	}

	public String getReligions_religion_name() {
		return religions_religion_name;
	}

	public void setReligions_religion_name(String religions_religion_name) {
		this.religions_religion_name = religions_religion_name;
	}

	public String getContact_and_basic_info_religious_view() {
		return contact_and_basic_info_religious_view;
	}

	public void setContact_and_basic_info_religious_view(String contact_and_basic_info_religious_view) {
		this.contact_and_basic_info_religious_view = contact_and_basic_info_religious_view;
	}

	public char getContact_and_basic_info_interested_in() {
		return contact_and_basic_info_interested_in;
	}

	public void setContact_and_basic_info_interested_in(char contact_and_basic_info_interested_in) {
		this.contact_and_basic_info_interested_in = contact_and_basic_info_interested_in;
	}

	
}

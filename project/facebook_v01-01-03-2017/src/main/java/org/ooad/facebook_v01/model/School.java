package org.ooad.facebook_v01.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class School {

	private int school_pk;
	private String school_name;
	private int school_start_year;
	private int school_end_year;
	private String school_description;
	private int userdetails_pk;
	
	public int getSchool_pk() {
		return school_pk;
	}
	public String getSchool_name() {
		return school_name;
	}
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	public int getSchool_start_year() {
		return school_start_year;
	}
	public void setSchool_start_year(int school_start_year) {
		this.school_start_year = school_start_year;
	}
	public int getSchool_end_year() {
		return school_end_year;
	}
	public void setSchool_end_year(int school_end_year) {
		this.school_end_year = school_end_year;
	}
	public String getSchool_description() {
		return school_description;
	}
	public void setSchool_description(String school_description) {
		this.school_description = school_description;
	}
	public void setSchool_pk(int school_pk) {
		this.school_pk = school_pk;
	}
	public int getUserdetails_pk() {
		return userdetails_pk;
	}
	public void setUserdetails_pk(int userdetails_pk) {
		this.userdetails_pk = userdetails_pk;
	}
	
}

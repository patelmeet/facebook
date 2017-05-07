package org.ooad.facebook_v01.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class College {
	private int college_pk;
	private String college_name;
	private int college_start_year;
	private int college_end_year;
	private char college_graduated;
	private String college_description;
	private String college_concentration;
	private String college_attended_for;
	
	public College(){
		
	}
	
	public College(int college_pk, String college_name, int college_start_year, int college_end_year,
			char college_graduated, String college_description, String college_concentration,
			String college_attended_for) {
		this.college_pk = college_pk;
		this.college_name = college_name;
		this.college_start_year = college_start_year;
		this.college_end_year = college_end_year;
		this.college_graduated = college_graduated;
		this.college_description = college_description;
		this.college_concentration = college_concentration;
		this.college_attended_for = college_attended_for;
	}
	
	public int getCollege_pk() {
		return college_pk;
	}
	public void setCollege_pk(int college_pk) {
		this.college_pk = college_pk;
	}
	public String getCollege_name() {
		return college_name;
	}
	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}
	public int getCollege_start_year() {
		return college_start_year;
	}
	public void setCollege_start_year(int college_start_year) {
		this.college_start_year = college_start_year;
	}
	public int getCollege_end_year() {
		return college_end_year;
	}
	public void setCollege_end_year(int college_end_year) {
		this.college_end_year = college_end_year;
	}
	public char getCollege_graduated() {
		return college_graduated;
	}
	public void setCollege_graduated(char college_graduated) {
		this.college_graduated = college_graduated;
	}
	public String getCollege_description() {
		return college_description;
	}
	public void setCollege_description(String college_description) {
		this.college_description = college_description;
	}
	public String getCollege_concentration() {
		return college_concentration;
	}
	public void setCollege_concentration(String college_concentration) {
		this.college_concentration = college_concentration;
	}
	public String getCollege_attended_for() {
		return college_attended_for;
	}
	public void setCollege_attended_for(String college_attended_for) {
		this.college_attended_for = college_attended_for;
	}
	
	
}

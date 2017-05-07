package org.ooad.facebook_v01.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Work {
	private int work_pk;
	private String work_company;
	private String work_position;
	private String work_city;
	private String work_description;
	private int work_start_year;
	private int work_end_year;
	private int userdetails_pk;
	
	public Work(){
		
	}
	
	public int getWork_pk() {
		return work_pk;
	}
	public void setWork_pk(int work_pk) {
		this.work_pk = work_pk;
	}
	public String getWork_company() {
		return work_company;
	}
	public void setWork_company(String work_company) {
		this.work_company = work_company;
	}
	public String getWork_position() {
		return work_position;
	}
	public void setWork_position(String work_position) {
		this.work_position = work_position;
	}
	public String getWork_city() {
		return work_city;
	}
	public void setWork_city(String work_city) {
		this.work_city = work_city;
	}
	public String getWork_description() {
		return work_description;
	}
	public void setWork_description(String work_description) {
		this.work_description = work_description;
	}
	public int getWork_start_year() {
		return work_start_year;
	}
	public void setWork_start_year(int work_start_year) {
		this.work_start_year = work_start_year;
	}
	public int getWork_end_year() {
		return work_end_year;
	}
	public void setWork_end_year(int work_end_year) {
		this.work_end_year = work_end_year;
	}
	public int getUserdetails_pk() {
		return userdetails_pk;
	}
	public void setUserdetails_pk(int userdetails_pk) {
		this.userdetails_pk = userdetails_pk;
	}
	
	
	
	
}

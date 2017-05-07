package org.ooad.facebook_v01.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Events {

	private int event_pk;
	private String event_name;
	private String event_location;
	private String event_start_date;
	private String event_start_time;
	private String event_end_date;
	private String event_end_time;
	private String event_description;
	private String event_photo;
	
	public Events(){
		
	}
	
	public Events(int event_pk,String event_name,String event_location,String event_start_date,String event_start_time,String event_end_date,String event_end_time,String event_description,String event_photo)
	{
		this.setEvent_pk(event_pk);
		this.setEvent_name(event_name);
		this.setEvent_description(event_description);
		this.setEvent_location(event_location);
		this.setEvent_start_date(event_start_date);
		this.setEvent_start_time(event_start_time);
		this.setEvent_end_date(event_end_date);
		this.setEvent_end_time(event_end_time);
		this.setEvent_photo(event_photo);
	}

	public int getEvent_pk() {
		return event_pk;
	}
	public void setEvent_pk(int college_pk) {
		this.event_pk = college_pk;
	}
	
	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	
	
	public String getEvent_location() {
		return event_location;
	}

	public void setEvent_location(String event_location) {
		this.event_location = event_location;
	}

	public String getEvent_description() {
		return event_description;
	}

	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}

	public String getEvent_photo() {
		return event_photo;
	}

	public void setEvent_photo(String event_photo) {
		this.event_photo = event_photo;
	}

	public String getEvent_start_date() {
		return event_start_date;
	}

	public void setEvent_start_date(String event_start_date) {
		this.event_start_date = event_start_date;
	}

	public String getEvent_start_time() {
		return event_start_time;
	}

	public void setEvent_start_time(String event_start_time) {
		this.event_start_time = event_start_time;
	}

	public String getEvent_end_time() {
		return event_end_time;
	}

	public void setEvent_end_time(String event_end_time) {
		this.event_end_time = event_end_time;
	}

	public String getEvent_end_date() {
		return event_end_date;
	}

	public void setEvent_end_date(String event_end_date) {
		this.event_end_date = event_end_date;
	}

	
	
}

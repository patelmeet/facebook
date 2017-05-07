package org.ooad.facebook_v01.model;

import java.util.Date;

//@Author : Nitish

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GroupDetailsModel {
	
	private int groupdetails_id;
	private String groupdetails_name;
	private int groupdetails_createdby;
	private int groupdetails_privacy;
	private int groupdetails_membercount;
	//private Date groupdetails_datecreated;
	private String groupdetails_description;
	private String groupdetails_web_email;
	private String groupdetails_imageurl;
	
	/*
	 * 	groupdetails_privacy {0-public , 1-closed , 2-secret }
	 */
	
	public GroupDetailsModel(){
		
	}

	public int getGroupdetails_id() {
		return groupdetails_id;
	}

	public void setGroupdetails_id(int groupdetails_id) {
		this.groupdetails_id = groupdetails_id;
	}

	public String getGroupdetails_name() {
		return groupdetails_name;
	}

	public void setGroupdetails_name(String groupdetails_name) {
		this.groupdetails_name = groupdetails_name;
	}

	public int getGroupdetails_createdby() {
		return groupdetails_createdby;
	}

	public void setGroupdetails_createdby(int groupdetails_createdby) {
		this.groupdetails_createdby = groupdetails_createdby;
	}

	public int getGroupdetails_privacy() {
		return groupdetails_privacy;
	}

	public void setGroupdetails_privacy(int groupdetails_privacy) {
		this.groupdetails_privacy = groupdetails_privacy;
	}

	public int getGroupdetails_membercount() {
		return groupdetails_membercount;
	}

	public void setGroupdetails_membercount(int groupdetails_membercount) {
		this.groupdetails_membercount = groupdetails_membercount;
	}

	/*  
	 * For future reference
	 * 
	public Date getGroupdetails_datecreated() {
		return groupdetails_datecreated;
	}

	public void setGroupdetails_datecreated(Date groupdetails_datecreated) {
		this.groupdetails_datecreated = groupdetails_datecreated;
	}

	public String getGroupdetails_description() {
		return groupdetails_description;
	}
	*/

	public void setGroupdetails_description(String groupdetails_description) {
		this.groupdetails_description = groupdetails_description;
	}

	public String getGroupdetails_web_email() {
		return groupdetails_web_email;
	}

	public void setGroupdetails_web_email(String groupdetails_web_email) {
		this.groupdetails_web_email = groupdetails_web_email;
	}

	public String getGroupdetails_imageurl() {
		return groupdetails_imageurl;
	}

	public void setGroupdetails_imageurl(String groupdetails_imageurl) {
		this.groupdetails_imageurl = groupdetails_imageurl;
	}

}

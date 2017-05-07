package org.ooad.facebook_v01.model;

import java.util.Date;

//@Author : Nitish

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GroupMembersModel {
	
	private int groupmembers_groupid;
	private int groupmembers_memberid;
	private int groupmembers_role;
	private int groupmembers_status;
	
	/* 
	 * 
	 	groupmembers_role { 0-admin , 1-moderator , 2-member}
	 
	 	groupmembers_status{ 0-pending , 1-joined , 2-blocked }
	 
	 */
	
	public GroupMembersModel(){
		
	}

	public int getGroupmembers_groupid() {
		return groupmembers_groupid;
	}

	public void setGroupmembers_groupid(int groupmembers_groupid) {
		this.groupmembers_groupid = groupmembers_groupid;
	}

	public int getGroupmembers_memberid() {
		return groupmembers_memberid;
	}

	public void setGroupmembers_memberid(int groupmembers_memberid) {
		this.groupmembers_memberid = groupmembers_memberid;
	}

	public int getGroupmembers_role() {
		return groupmembers_role;
	}

	public void setGroupmembers_role(int groupmembers_role) {
		this.groupmembers_role = groupmembers_role;
	}

	public int getGroupmembers_status() {
		return groupmembers_status;
	}

	public void setGroupmembers_status(int groupmembers_status) {
		this.groupmembers_status = groupmembers_status;
	}

}

package org.ooad.facebook_v01.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Login {
	
	private String login_Id;
	private String login_Password;
	
	public Login(){
		
	}
	public String getLogin_Id() {
		return login_Id;
	}
	
	public void setLogin_Id(String login_Id) {
		this.login_Id = login_Id;
	}
	public String getLogin_Password() {
		return login_Password;
	}
	public void setLogin_Password(String login_Password) {
		this.login_Password = login_Password;
	}
	
	

}

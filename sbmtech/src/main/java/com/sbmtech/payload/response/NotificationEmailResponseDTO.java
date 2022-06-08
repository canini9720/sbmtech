package com.sbmtech.payload.response;

import java.io.Serializable;

public class NotificationEmailResponseDTO implements Serializable{
	
	private String email;
	private boolean isEmailSent = false;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEmailSent() {
		return isEmailSent;
	}
	public void setEmailSent(boolean isEmailSent) {
		this.isEmailSent = isEmailSent;
	}
}

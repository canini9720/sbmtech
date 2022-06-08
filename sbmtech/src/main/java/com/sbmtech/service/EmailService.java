package com.sbmtech.service;

import com.sbmtech.dto.NotifEmailDTO;

public interface EmailService {
	
	
	public boolean sendEmailWithMultiAttachments(NotifEmailDTO dto);
	 public void sendPlainTextEmail(String subject, String message) throws Exception;

}

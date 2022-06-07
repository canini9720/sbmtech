package com.sbmtech.service;

import com.sbmtech.dto.NotificationEmailSenderDTO;

public interface EmailService {
	
	
	public boolean sendEmailWithMultiAttachments(NotificationEmailSenderDTO dto);
	 public void sendPlainTextEmail(String subject, String message) throws Exception;

}

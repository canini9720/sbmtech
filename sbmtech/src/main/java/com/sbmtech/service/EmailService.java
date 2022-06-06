package com.sbmtech.service;

import com.sbmtech.dto.NotificationEmailSenderDTO;

public interface EmailService {
	
	public void testEmail(String toMail,String subject,String body);
	public boolean sendEmailWithMultiAttachments(NotificationEmailSenderDTO dto);

}

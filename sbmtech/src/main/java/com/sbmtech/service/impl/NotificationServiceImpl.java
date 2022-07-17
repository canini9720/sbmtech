package com.sbmtech.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbmtech.common.util.NotificationUtil;
import com.sbmtech.dto.NotifEmailDTO;
import com.sbmtech.payload.response.NotificationEmailResponseDTO;
import com.sbmtech.service.EmailService;
import com.sbmtech.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	NotificationUtil util;
	
	@Autowired
	EmailService emailService;

	@Override
	public NotificationEmailResponseDTO sendOTPEmail(NotifEmailDTO dto) throws Exception {
		NotificationEmailResponseDTO emailResponseDTO = new NotificationEmailResponseDTO();
		NotifEmailDTO emailSenderDTO = new NotifEmailDTO();
		boolean isEmailSent = false;
		
		emailSenderDTO.setEmailTo(dto.getEmailTo());
		emailSenderDTO.setEmailBody(util.prepareOTPEmail(dto));
		emailSenderDTO.setSubject(util.getServiceNotifEmailSubject());
		emailSenderDTO.setAttachmentFileName(dto.getAttachmentFileName());
		emailSenderDTO.setAttachmentObj(dto.getAttachmentObj());
		emailSenderDTO.setCustomerName(dto.getCustomerName());
		emailSenderDTO.setOtpCode(dto.getOtpCode());
		
		//isEmailSent = emailSender.sendEmail(emailSenderDTO);
		
		isEmailSent =emailService.sendEmailWithMultiAttachments(emailSenderDTO);
		
		emailResponseDTO.setEmailSent(isEmailSent);
		emailResponseDTO.setEmail(emailSenderDTO.getEmailTo());
		
		return emailResponseDTO;
		
	}

	@Override
	public NotificationEmailResponseDTO sendAcctActivationEmail(NotifEmailDTO dto) throws Exception {
		NotificationEmailResponseDTO emailResponseDTO = new NotificationEmailResponseDTO();
		NotifEmailDTO emailSenderDTO = new NotifEmailDTO();
		boolean isEmailSent = false;
		
		emailSenderDTO.setEmailTo(dto.getEmailTo());
		emailSenderDTO.setEmailBody(util.prepareAcctActivationEmail(dto));
		emailSenderDTO.setSubject(dto.getSubject());
		emailSenderDTO.setAttachmentFileName(dto.getAttachmentFileName());
		emailSenderDTO.setAttachmentObj(dto.getAttachmentObj());
		emailSenderDTO.setCustomerName(dto.getCustomerName());
		
		
		isEmailSent =emailService.sendEmailWithMultiAttachments(emailSenderDTO);
		
		emailResponseDTO.setEmailSent(isEmailSent);
		emailResponseDTO.setEmail(emailSenderDTO.getEmailTo());
		
		return emailResponseDTO;
	}

}

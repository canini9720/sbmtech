package com.sbmtech.service.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonBeanUtil;
import com.sbmtech.common.util.MailProperties;
import com.sbmtech.dto.EmailAttachmentDTO;
import com.sbmtech.dto.NotificationEmailSenderDTO;
import com.sbmtech.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
	private static final Logger loggerInfo = Logger.getLogger(CommonConstants.LOGGER_SERVICES_INFO);
	private static final Logger logger = Logger.getLogger(CommonConstants.LOGGER_SERVICES_ERROR);
	
	static Properties properties=new Properties();

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	 @Autowired
	 MailProperties mailProperties;
	 
	 @PostConstruct
	 public void init() throws Exception {
		 System.out.println("mailProperties="+mailProperties.getUsername());
		 properties= CommonBeanUtil.toProperties(mailProperties);
		 System.out.println("properties="+properties);
	 }
	
	
	@Override
	public void testEmail(String toMail,String subject,String body) {
		SimpleMailMessage msg=new SimpleMailMessage();
		msg.setFrom("hasan234abu@gmail.com");
		msg.setTo(toMail);
		msg.setSubject(subject);
		msg.setText(body);
		javaMailSender.send(msg);
		
		System.out.println("Email send successfully");
		
	}
	
	private void sendEmailByGmail(NotificationEmailSenderDTO dto) throws MessagingException{
		/*
		Session gmailSession = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(mailProperties.getUsername(), mailProperties.getPassword());

            }

        });
		MimeMessage message = new MimeMessage(gmailSession);*/
		MimeMessage message = new MimeMessage(getGmailSession());
		message.setFrom(new InternetAddress("hasan234abu@gmail.com"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dto.getEmailTo()));
		message.setSubject(dto.getSubject(),"UTF-8");
		message.setHeader("Content-Type", "text/html; charset=UTF-8");

		
		if(StringUtils.isNotBlank(dto.getEmailCC()) && !dto.getEmailCC().equals(CommonConstants.COMMA)){
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(dto.getEmailCC()));
		}
		if(StringUtils.isNotBlank(dto.getEmailBCC()) && !dto.getEmailBCC().equals(CommonConstants.COMMA)){
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(dto.getEmailBCC()));
		}
		
		Multipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();
		
		messageBodyPart.setContent(dto.getEmailBody(), "text/html; charset=UTF-8");
		multipart.addBodyPart(messageBodyPart);
		List<EmailAttachmentDTO> attachments=dto.getMultipleAttachList();
		if(attachments==null) {
			List<EmailAttachmentDTO> attachmentsList = new ArrayList<EmailAttachmentDTO>();
			attachmentsList.add(new EmailAttachmentDTO(dto.getAttachmentFileName(), dto.getAttachmentObj(), dto.getAttachmentFilePath()));
			attachments=attachmentsList;
		}
		if(attachments != null && !attachments.isEmpty()){
			
			for (Iterator iterator = attachments.iterator(); iterator.hasNext();) {
				EmailAttachmentDTO emailAttachmentDTO = (EmailAttachmentDTO) iterator.next();
				
				String attachmentFileName = emailAttachmentDTO.getAttachmentFileName();
				String attachmentFilePath = emailAttachmentDTO.getAttachmentFilePath();
				byte[] attachmentBytes = emailAttachmentDTO.getAttachmentBytes();
				
				if((attachmentFileName != null && !attachmentFileName.isEmpty())
						&& (attachmentFilePath != null && !attachmentFilePath.isEmpty())){
					
					messageBodyPart = new MimeBodyPart();
			        DataSource source = new FileDataSource(attachmentFilePath+attachmentFileName);
			        messageBodyPart.setDataHandler(new DataHandler(source));
			        messageBodyPart.setFileName(attachmentFileName);
			        multipart.addBodyPart(messageBodyPart);
			        
				}else if((attachmentFileName != null && !attachmentFileName.isEmpty()) 
						&& (attachmentBytes != null && attachmentBytes.length > 0)){
					
					messageBodyPart = new MimeBodyPart();
			        DataSource source = new ByteArrayDataSource(attachmentBytes, "application/"+attachmentFileName.substring(attachmentFileName.lastIndexOf(".")+1));
			        messageBodyPart.setDataHandler(new DataHandler(source));
			        messageBodyPart.setFileName(attachmentFileName);
			        multipart.addBodyPart(messageBodyPart);
				}
			}
		}
		if(dto.getEmailBody()!=null&& !dto.getEmailBody().isEmpty())
		//multipart=getImageMultipart(multipart,dto.getType(),dto);
		
        message.setContent(multipart);
        
		Transport.send(message);
	}
	public Session getGmailSession(){
		
		Properties props = new Properties();
		
		boolean auth = Boolean.parseBoolean(mailProperties.getAuth().trim());
		
		Session session = null;
		
		if(auth){
			
			props.put("mail.smtp.host", mailProperties.getHost());
			props.put("mail.smtp.port", mailProperties.getHost());
			props.put("mail.smtp.auth", mailProperties.getAuth());
			props.put("mail.smtp.ssl.enable", mailProperties.getStarttls());
			
			session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mailProperties.getUsername(), mailProperties.getPassword());
					}
				});
		}else{
		
			props.put("mail.smtp.host", mailProperties.getHost());
			props.put("mail.smtp.auth", mailProperties.getAuth());
			session = Session.getInstance(props);
		}
		
		return session;
	}
	public boolean sendEmailWithMultiAttachments(NotificationEmailSenderDTO dto){
		
		boolean isEmailSent = false;
		
		if(dto.getEmailTo() != null && !dto.getEmailTo().isEmpty()){
			
			try{
				
				//sendEmailByGmail(dto.getEmailTo(), dto.getSubject(), dto.getEmailBody(), dto.getMultipleAttachList(),dto.getType());
				sendEmailByGmail(dto);
				isEmailSent = true;
				
			}catch(Exception ex){
				
				isEmailSent = false;
				logger.error("SEND_EMAIL_EXCEPTION --> Email_To:"+dto.getEmailTo()+", Subject : "+dto.getSubject(), ex);
			}
			
		}else{
			
			isEmailSent = false;
		}
		
		return isEmailSent;
	}
	
	public boolean sendEmail(NotificationEmailSenderDTO dto) {
		boolean isEmailSent = false;
		if(dto.getEmailTo() != null && !dto.getEmailTo().isEmpty()){
			try{
				sendEmailByGmail(dto);
				isEmailSent = true;
			}catch(Exception ex){
				
				isEmailSent = false;
				logger.error("SEND_EMAIL_EXCEPTION --> Email_To:"+dto.getEmailTo()+", Subject : "+dto.getSubject(), ex);
			}
		}
		return isEmailSent;
	}


	
	
	


	
	

}

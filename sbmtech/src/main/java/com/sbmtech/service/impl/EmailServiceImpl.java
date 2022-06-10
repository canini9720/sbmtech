package com.sbmtech.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonBeanUtil;
import com.sbmtech.common.util.MailProperties;
import com.sbmtech.dto.EmailAttachmentDTO;
import com.sbmtech.dto.NotifEmailDTO;
import com.sbmtech.service.EmailService;

@Service
@DependsOn("AppSystemProp")
public class EmailServiceImpl implements EmailService{
	
	private static final Logger loggerInfo = Logger.getLogger(CommonConstants.LOGGER_SERVICES_INFO);
	private static final Logger logger = Logger.getLogger(CommonConstants.LOGGER_SERVICES_ERROR);
	
	//static Properties properties=new Properties();

	
	
	@Autowired
	private Environment env;
	
	@Autowired
	private MailProperties mailProperties;
	
	private String gmail_OR_domain="";
	private String fromMailDomain="";
	private String fromMailGmail="";
	private String gMailAuth="";
	private String gMailHost="";
	private String gMailPort="";
	private String gMailStartTls="";
	private String gEmailUsername="";
	private String gEmailPwd="";
	
	private String mailAuth="";
	private String mailHost="";
	private String mailPort="";
	private String mailStartTls="";
	private String emailUsername="";
	private String emailPwd="";
	
	
	@PostConstruct
	public void initialize() {
		
		gmail_OR_domain = AppSystemPropImpl.props.get("gmail.OR.domain");
		fromMailDomain = AppSystemPropImpl.props.get("from.mail.id");
		fromMailGmail = AppSystemPropImpl.props.get("from.gmail.id");
		mailAuth = AppSystemPropImpl.props.get("email.smtp.auth");
		mailAuth = AppSystemPropImpl.props.get("email.smtp.auth");
		mailHost = AppSystemPropImpl.props.get("mail.smtp.host");
		mailPort = AppSystemPropImpl.props.get("email.port");
		mailStartTls= AppSystemPropImpl.props.get("email.smtp.starttls");
		emailUsername = AppSystemPropImpl.props.get("email.username");
		emailPwd = AppSystemPropImpl.props.get("email.password");
		
		gMailAuth = AppSystemPropImpl.props.get("gemail.smtp.auth");
		gMailHost = AppSystemPropImpl.props.get("gmail.smtp.host");
		gMailPort = AppSystemPropImpl.props.get("gemail.port");
		gMailStartTls= AppSystemPropImpl.props.get("gemail.smtp.starttls");
		gEmailUsername = AppSystemPropImpl.props.get("gemail.username");
		gEmailPwd = AppSystemPropImpl.props.get("gemail.password");
	}
	 /*
	 @PostConstruct
	 public void init() throws Exception {
		 System.out.println("mailProperties="+mailProperties.getUsername());
		 properties= CommonBeanUtil.toProperties(mailProperties);
		 System.out.println("properties="+properties);
	 }*/
	
	
	
	private void sendEmail(NotifEmailDTO dto) throws Exception{
		Boolean gmailOrDomain=(gmail_OR_domain.equalsIgnoreCase("gmail"))?true:false;
		String fromEmail=(gmailOrDomain)?fromMailGmail:fromMailDomain;
		MimeMessage message = new MimeMessage(getEmailSession());
		message.setFrom(new InternetAddress(fromEmail,"NoReply"));
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
	public Session getEmailSession(){
		Properties props = new Properties();
		Session session = null;
		Boolean gmailOrDomain=(gmail_OR_domain.equalsIgnoreCase("gmail"))?true:false;
		if(gmailOrDomain) {
			boolean auth = Boolean.parseBoolean(gMailAuth.trim());
			if(auth){
				props.put("mail.smtp.host", gMailHost);
				props.put("mail.smtp.port", gMailPort);
				props.put("mail.smtp.auth", gMailAuth);
				props.put("mail.smtp.starttls.enable", gMailAuth);
				session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(gEmailUsername,gEmailPwd);
						}
					});
			}else{
				props.put("mail.smtp.host", gMailHost);
				props.put("mail.smtp.auth", gMailAuth);
				session = Session.getInstance(props);
			}
		}else {
			props.put("mail.smtp.host", mailHost); //SMTP Host
			props.put("mail.smtp.socketFactory.port", mailPort); //SSL Port
			props.put("mail.smtp.socketFactory.class",mailStartTls); //SSL Factory Class
			props.put("mail.smtp.auth",mailAuth); //Enabling SMTP Authentication
			props.put("mail.smtp.port", mailPort); //SMTP Port
			Authenticator auth = new Authenticator() {
				//override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailUsername, emailPwd);
				}
			};
			
			session = Session.getDefaultInstance(props, auth);
		}
		
		return session;
	}
	
	 public void sendPlainTextEmail(String subject, String message) throws Exception {

	        // sets SMTP server properties
	        Properties properties = new Properties();
	        properties.put("mail.smtp.host", mailHost);
	        properties.put("mail.smtp.port", mailPort);
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	// *** BEGIN CHANGE
	        properties.put("mail.smtp.user", "hasan234abu@gmail.com");

	        // creates a new session, no Authenticator (will connect() later)
	        Session session = Session.getDefaultInstance(properties);
	// *** END CHANGE

	        // creates a new e-mail message
	        Message msg = new MimeMessage(session);

	        msg.setFrom(new InternetAddress(emailUsername));
	        InternetAddress[] toAddresses = { new InternetAddress("ashrafsnj@gmail.com") };
	        msg.setRecipients(Message.RecipientType.TO, toAddresses);
	        msg.setSubject(subject);
	        msg.setSentDate(new Date());
	        // set plain text message
	        msg.setText(message);

	// *** BEGIN CHANGE
	        // sends the e-mail
	        Transport t = session.getTransport("smtp");
	        t.connect(emailUsername, emailPwd);
	        t.sendMessage(msg, msg.getAllRecipients());
	        t.close();
	// *** END CHANGE

	    }
	public boolean sendEmailWithMultiAttachments(NotifEmailDTO dto){
		
		boolean isEmailSent = false;
		
		if(dto.getEmailTo() != null && !dto.getEmailTo().isEmpty()){
			
			try{
				
				//sendEmailByGmail(dto.getEmailTo(), dto.getSubject(), dto.getEmailBody(), dto.getMultipleAttachList(),dto.getType());
				sendEmail(dto);
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
	
	public boolean sendEmailNotification(NotifEmailDTO dto) {
		boolean isEmailSent = false;
		if(dto.getEmailTo() != null && !dto.getEmailTo().isEmpty()){
			try{
				sendEmail(dto);
				isEmailSent = true;
			}catch(Exception ex){
				
				isEmailSent = false;
				logger.error("SEND_EMAIL_EXCEPTION --> Email_To:"+dto.getEmailTo()+", Subject : "+dto.getSubject(), ex);
			}
		}
		return isEmailSent;
	}


	
	
	


	
	

}

package com.sbmtech.dto;

import java.util.List;

public class NotificationEmailSenderDTO {
	
	private String emailTo;
	private String emailCC;
	private String emailBCC;
	private String subject;
	private String emailBody;
	private String url;
	private String attachmentFileName;
	private String attachmentFilePath;
	private byte[] attachmentObj;
	private byte[] attachmentExcelObj;
	private String type;
	private Long referenceNo;
	
	private List<MultipleAttachmentsEmailDTO> multipleAttachmentsEmailDTO;
	
	private List<EmailAttachmentDTO> multipleAttachList;
	
	public List<MultipleAttachmentsEmailDTO> getMultipleAttachmentsEmailDTO() {
		return multipleAttachmentsEmailDTO;
	}
	public void setMultipleAttachmentsEmailDTO(
			List<MultipleAttachmentsEmailDTO> multipleAttachmentsEmailDTO) {
		this.multipleAttachmentsEmailDTO = multipleAttachmentsEmailDTO;
	}
	public byte[] getAttachmentExcelObj() {
		return attachmentExcelObj;
	}
	public void setAttachmentExcelObj(byte[] attachmentExcelObj) {
		this.attachmentExcelObj = attachmentExcelObj;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	public String getAttachmentFileName() {
		return attachmentFileName;
	}
	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}
	public String getAttachmentFilePath() {
		return attachmentFilePath;
	}
	public void setAttachmentFilePath(String attachmentFilePath) {
		this.attachmentFilePath = attachmentFilePath;
	}
	public byte[] getAttachmentObj() {
		return attachmentObj;
	}
	public void setAttachmentObj(byte[] attachmentObj) {
		this.attachmentObj = attachmentObj;
	}
	public List<EmailAttachmentDTO> getMultipleAttachList() {
		return multipleAttachList;
	}
	public void setMultipleAttachList(List<EmailAttachmentDTO> multipleAttachList) {
		this.multipleAttachList = multipleAttachList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(Long referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getEmailCC() {
		return emailCC;
	}
	public void setEmailCC(String emailCC) {
		this.emailCC = emailCC;
	}
	public String getEmailBCC() {
		return emailBCC;
	}
	public void setEmailBCC(String emailBCC) {
		this.emailBCC = emailBCC;
	}
	
	
}

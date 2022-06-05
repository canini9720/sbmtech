package com.sbmtech.dto;

public class EmailAttachmentDTO {
	
	private String attachmentFileName;
	private byte[] attachmentBytes;
	private String attachmentFilePath;
	
	public EmailAttachmentDTO(){
		
	}
	
	public EmailAttachmentDTO(String attachmentFileName,
			byte[] attachmentBytes, String attachmentFilePath) {
		super();
		this.attachmentFileName = attachmentFileName;
		this.attachmentBytes = attachmentBytes;
		this.attachmentFilePath = attachmentFilePath;
	}
	
	public String getAttachmentFileName() {
		return attachmentFileName;
	}
	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}
	public byte[] getAttachmentBytes() {
		return attachmentBytes;
	}
	public void setAttachmentBytes(byte[] attachmentBytes) {
		this.attachmentBytes = attachmentBytes;
	}
	public String getAttachmentFilePath() {
		return attachmentFilePath;
	}
	public void setAttachmentFilePath(String attachmentFilePath) {
		this.attachmentFilePath = attachmentFilePath;
	}
}

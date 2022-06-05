package com.sbmtech.dto;

public class MultipleAttachmentsEmailDTO {

	private String attachmentFilePath;
	private byte[] attachmentObj;
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
	
	
}
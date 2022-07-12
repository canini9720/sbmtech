package com.sbmtech.payload.response;

import com.sbmtech.common.util.CommonUtil;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GDriveResponse {
	
	private Integer responseCode;
	private String gFileId;
	private String contentType;
	private Long userId;
	private Integer docTypeId;
	private String responseDesc;
	private String thumbnailLink;
	public GDriveResponse(){
		
	}
	
	public GDriveResponse(int responseCode) {
		super();
		
		this.responseCode = responseCode;
		this.responseDesc = CommonUtil.getSuccessOrFailureMessageWithId(this.responseCode);
	}
	

}

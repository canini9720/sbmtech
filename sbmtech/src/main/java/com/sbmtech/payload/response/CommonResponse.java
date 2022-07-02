package com.sbmtech.payload.response;

import com.sbmtech.common.util.CommonUtil;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonResponse {
	
	private Integer responseCode;
	private String responseMessage;
	private String responseDesc;
	private Object  responseObj;
	
	public CommonResponse(){
		
	}
	
	public CommonResponse(int responseCode) {
		super();
		
		this.responseCode = responseCode;
		this.responseDesc = CommonUtil.getSuccessOrFailureMessageWithId(this.responseCode);
	}
	

}

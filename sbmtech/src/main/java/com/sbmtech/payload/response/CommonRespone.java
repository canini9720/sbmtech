package com.sbmtech.payload.response;

import com.sbmtech.common.util.CommonUtil;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonRespone {
	
	private Integer responseCode;
	private String responseMessage;
	private String responseDesc;
	public CommonRespone(){
		
	}
	
	public CommonRespone(int responseCode) {
		super();
		
		this.responseCode = responseCode;
		this.responseDesc = CommonUtil.getSuccessOrFailureMessageWithId(this.responseCode);
	}
	

}

package com.sbmtech.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpDTO {
		
	
	private Long id;
	
	private Long userId;
	
	
	private Integer otpCode;
		
	
	private Date generatedTime;
	
	
	private Date expiryTime;
	
	private String email;
	private String flowType;
}

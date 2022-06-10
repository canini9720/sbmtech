package com.sbmtech.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ValidateOtpRequest {
	
	private Long verificationId;
	private Integer userOtp;

}

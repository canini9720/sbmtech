package com.sbmtech.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetRequest {

	private Long userId;
	private Long verificationId;
	private String email;
	private String password;
	private String confirmPassword;
	private Integer otpCode;
	
	
}

package com.sbmtech.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResetRequest {

	private String username;
	private String password;
	private String confirmPassword;
	
	
}

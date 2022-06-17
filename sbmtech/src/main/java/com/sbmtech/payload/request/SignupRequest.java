package com.sbmtech.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {

	private String username;
	private String firstname;
	private String lastname;
	private Integer memberCategory;
	private String password;
	private String confirmPassword;
	private String email;

	
}

package com.sbmtech.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class VerifyUserRequest {
	
	private String username;
	private String email;
	private Integer type;
	

}

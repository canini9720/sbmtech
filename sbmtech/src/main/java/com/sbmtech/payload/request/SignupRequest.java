package com.sbmtech.payload.request;

import java.util.Set;

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
	//private String email;
	//private Set<String> role;
	
}

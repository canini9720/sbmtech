package com.sbmtech.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegRequest {

	private Long userId;
	private String firstname;
	private String lastname;
	private String email;
	private boolean enabled;
	private boolean verified;

	
}

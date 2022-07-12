package com.sbmtech.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class VerifyUserRequest {
	
	private String username;
	private String email;
	private Integer type;
	private Integer memberCategory;
	

}

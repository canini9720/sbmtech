package com.sbmtech.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDetailDTO {
		
	private Long userId;
	private String firstname;
	private String lastname;
	private String username;
	private Integer memberCategory;
	private String email;
	private Boolean enabled;
	private Boolean verified;
	
	
}

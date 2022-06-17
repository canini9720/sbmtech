package com.sbmtech.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailDTO {
		
	private Long userId;
	private String firstname;
	private String lastname;
	private String username;
	private Integer memberCategory;
	private String email;
	private Boolean enabled;
	private Boolean verified;
	private PersonDetailDTO personDetailDTO;
	private List<ContactDetailDTO> contactDetailDTO;
	
}

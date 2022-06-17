package com.sbmtech.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailDTO {
		
	private Long userId;
	private UserRegistrationDetailDTO userRegistrationDetailDTO;
	private PersonDetailDTO personDetailDTO;
	private List<ContactDTO> contactDTO;
	
	
}

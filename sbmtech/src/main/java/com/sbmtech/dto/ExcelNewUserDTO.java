package com.sbmtech.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExcelNewUserDTO {

	private String username;
	private String firstname;
	private String lastname;
	private Integer memberCategory;
	private String password;
	private String confirmPassword;
	private String email;
	private PersonDetailDTO personDetails;
	private List<ContactDTO> contactDetails;

	
}

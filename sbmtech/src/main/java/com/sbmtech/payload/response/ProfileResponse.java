package com.sbmtech.payload.response;

import java.util.List;

import com.sbmtech.dto.ContactDTO;
import com.sbmtech.dto.PersonDetailDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProfileResponse {
	
	private Long userId;
	private String firstname;
	private String lastname;
	private String username;
	private Integer memberCategory;
	private String email;
	private PersonDetailDTO personDetails;
	private List<ContactDTO> contactDetails;
	

}

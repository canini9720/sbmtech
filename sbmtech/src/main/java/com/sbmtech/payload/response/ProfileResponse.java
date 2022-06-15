package com.sbmtech.payload.response;

import java.util.List;

import com.sbmtech.dto.ContactDetailDTO;
import com.sbmtech.dto.PersonDetailDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProfileResponse {
	
	private Long userId;
	private PersonDetailDTO personDetails;
	private List<ContactDetailDTO> contactDetails;
	

}

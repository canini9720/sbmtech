package com.sbmtech.payload.request;

import java.util.List;

import com.sbmtech.dto.ContactDTO;
import com.sbmtech.dto.PersonDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProfileRequest {
	
	private Long userId;
	private PersonDetailDTO personDetails;
	private List<ContactDTO> contactDetails;
	

}

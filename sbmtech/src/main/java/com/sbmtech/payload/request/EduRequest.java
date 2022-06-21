package com.sbmtech.payload.request;

import java.util.List;

import com.sbmtech.dto.EduDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EduRequest {
	
	private Long userId;
	private List<EduDTO> eduDetails;
	
	

}

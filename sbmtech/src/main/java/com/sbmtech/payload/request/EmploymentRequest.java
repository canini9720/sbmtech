package com.sbmtech.payload.request;

import java.util.List;

import com.sbmtech.dto.EmploymentDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmploymentRequest {
	
	private Long userId;
	private List<EmploymentDTO> employmentDetails;
	
	

}

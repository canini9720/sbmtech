package com.sbmtech.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmploymentDTO {
	
	
	private Long userId;
	private String designation;
	private String company;
	private String startDate;
	private String endDate;
	
	
	private List<WorkTimeDTO> workTimeDetails;

	
	
	
	
	
}

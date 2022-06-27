package com.sbmtech.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JobRequestDTO {
	
	
	private Long userId;
	private String designation;
	private String jobLocation;
	private String freeFrom;
	private String active;
	
	
	private List<JobReqWorkTimeDTO> jobReqWorkTimeDetails;

	
	
	
	
}

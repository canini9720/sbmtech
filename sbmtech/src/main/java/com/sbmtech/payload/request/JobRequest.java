package com.sbmtech.payload.request;

import java.util.List;

import com.sbmtech.dto.JobRequestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JobRequest {
	
	private Long userId;
	private List<JobRequestDTO> jobRequestDetails;
	
	

}

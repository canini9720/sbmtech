package com.sbmtech.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JobRequestDetailDTO {
	private Long userId;
	private List<JobRequestDTO> jobReqDTO;
	
	
}

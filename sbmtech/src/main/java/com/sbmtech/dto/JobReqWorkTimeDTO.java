package com.sbmtech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JobReqWorkTimeDTO {
	
	private Integer workTimeId;
	private JobReqWorkPaidDTO  jobReqWorkPaidDetail;
	
}

package com.sbmtech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JobReqWorkTimeDTO {
	
	private Integer workTimeId;
	//private JobReqWorkPaidDTO  jobReqWorkPaidDetail;
	
	//Newly Added
	private Integer paidBasisId;
	private Double amount;
	private String currency;
	
}

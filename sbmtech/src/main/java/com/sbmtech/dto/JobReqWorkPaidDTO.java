package com.sbmtech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JobReqWorkPaidDTO {
	
	private Integer paidBasisId;
	private Double  amount;
	private String currency;
	
}

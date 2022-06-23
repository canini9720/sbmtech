package com.sbmtech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class WorkTimeDTO {
	
	private Integer workTimeId;
	private WorkPaidDTO workPaidDetail;
	
}

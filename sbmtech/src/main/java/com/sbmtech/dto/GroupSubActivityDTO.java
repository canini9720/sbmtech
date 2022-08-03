package com.sbmtech.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Data
public class GroupSubActivityDTO {

	private Integer groupSubActivityId;
	private String groupSubActivity;
	private Integer selected=0;
	
}

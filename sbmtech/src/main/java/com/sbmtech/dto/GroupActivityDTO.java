package com.sbmtech.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Data
public class GroupActivityDTO {

	private Integer groupActivityId;
	private String groupActivity;
	private List<GroupSubActivityDTO> listGroupSubActivity;
	
}

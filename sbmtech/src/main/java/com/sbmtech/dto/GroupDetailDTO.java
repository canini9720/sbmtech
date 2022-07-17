package com.sbmtech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupDetailDTO {

	private Long userId;
	private String groupName;
	private String groupBranch;
	private String groupPobox;
	private Long groupContactNo;
}

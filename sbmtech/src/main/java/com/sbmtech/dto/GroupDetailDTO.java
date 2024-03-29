package com.sbmtech.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupDetailDTO {

	private Long groupId;
	private String groupName;
	private Long groupMgrId;
	private String groupMgrName;
	private String groupBranch;
	private String groupPobox;
	private String groupContactNo;
	private List<PartnerDTO> partnersList;
}

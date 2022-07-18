package com.sbmtech.payload.request;

import com.sbmtech.dto.GroupInfoDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupRequest {
	
	private Long groupId;
	private GroupInfoDTO groupDetails;
	
	

}

package com.sbmtech.payload.request;

import com.sbmtech.dto.GroupDetailDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupRequest {
	
	private Long groupId;
	private GroupDetailDTO groupDetails;
	
	

}

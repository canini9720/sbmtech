package com.sbmtech.payload.request;

import com.sbmtech.dto.GroupTeamPersonDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupTeamContactRequest {
	
	private Long groupId;
	private GroupTeamPersonDTO groupTeamPersonDTO;
	
	

}

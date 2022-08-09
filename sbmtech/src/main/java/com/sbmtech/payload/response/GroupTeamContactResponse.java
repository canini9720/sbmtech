package com.sbmtech.payload.response;

import java.util.List;

import com.sbmtech.dto.GroupTeamPersonDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupTeamContactResponse {
	
	private Long groupId;
	private List<GroupTeamPersonDTO> listGroupTeamPersonDTO;
	
	

}

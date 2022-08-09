package com.sbmtech.dto;

import java.util.List;

import com.sbmtech.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupTeamPersonDTO {
		
	private Long memberId;
	private String designation;
	private List<RoleDTO> powerList;
	private String status;
	private String mobile;
	private String email;
	
	
}

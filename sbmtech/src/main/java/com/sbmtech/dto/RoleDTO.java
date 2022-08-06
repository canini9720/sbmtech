package com.sbmtech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sbmtech.model.ERole;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleDTO {

	private Integer roleId;

	private ERole name;

	private String dispName;

	@JsonInclude(Include.NON_NULL)
	private Integer forSuperAdmin;

	@JsonInclude(Include.NON_NULL)
	private Integer forMember;

	@JsonInclude(Include.NON_NULL)
	private Integer forGroup;

	@JsonInclude(Include.NON_NULL)
	private Integer forGroupAdmin;
	
	private Integer selected=0;

}
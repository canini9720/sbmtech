package com.sbmtech.payload.request;

import java.util.Set;

import com.sbmtech.model.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GroupRegRequest {

	private Long userId;
	private String firstname;
	private String lastname;
	private String email;
	private boolean enabled;
	private boolean verified;
	private Set<Role> roles;

	
}

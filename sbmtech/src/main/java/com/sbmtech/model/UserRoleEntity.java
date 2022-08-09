package com.sbmtech.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_roles")
@Setter
@Getter
@NoArgsConstructor
public class UserRoleEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;


	@EmbeddedId
    private UserRoleUserIdRoleId userIdRoleId;
	
	
	
	
}

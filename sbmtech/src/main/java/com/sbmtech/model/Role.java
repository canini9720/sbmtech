package com.sbmtech.model;
import javax.persistence.*;
@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer roleId;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
	
	public Role() {
	}
	
	public Role(ERole name) {
		this.name = name;
	}
	
	
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public ERole getName() {
		return name;
	}
	
	public void setName(ERole name) {
		this.name = name;
	}
}
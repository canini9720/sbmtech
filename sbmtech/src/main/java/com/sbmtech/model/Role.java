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
	
	
	@Column(length = 20)
	private String dispName;
	
	@Column(name = "for_super_admin")
	private Integer forSuperAdmin;
	
	@Column(name = "for_member")
	private Integer forMember;
	
	@Column(name = "for_group")
	private Integer forGroup;
	
	@Column(name = "for_group_admin")
	private Integer forGroupAdmin;
	
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

	public String getDispName() {
		return dispName;
	}

	public void setDispName(String dispName) {
		this.dispName = dispName;
	}

	public Integer getForSuperAdmin() {
		return forSuperAdmin;
	}

	public void setForSuperAdmin(Integer forSuperAdmin) {
		this.forSuperAdmin = forSuperAdmin;
	}

	public Integer getForMember() {
		return forMember;
	}

	public void setForMember(Integer forMember) {
		this.forMember = forMember;
	}

	public Integer getForGroup() {
		return forGroup;
	}

	public void setForGroup(Integer forGroup) {
		this.forGroup = forGroup;
	}

	public Integer getForGroupAdmin() {
		return forGroupAdmin;
	}

	public void setForGroupAdmin(Integer forGroupAdmin) {
		this.forGroupAdmin = forGroupAdmin;
	}
	
	
}
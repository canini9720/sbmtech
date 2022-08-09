package com.sbmtech.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserRoleUserIdRoleId implements Serializable { 
	
    private static final long serialVersionUID = 1L;

	@Column(name="user_id")
    private Long userId;

    @Column(name="role_id")
    private Integer roleId;
    
    
    

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(roleId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRoleUserIdRoleId other = (UserRoleUserIdRoleId) obj;
		return Objects.equals(roleId, other.roleId) && Objects.equals(userId, other.userId);
	}
    
    
}

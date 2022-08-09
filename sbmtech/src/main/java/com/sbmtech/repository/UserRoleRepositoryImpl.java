package com.sbmtech.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sbmtech.model.UserRoleEntity;
import com.sbmtech.model.UserRoleUserIdRoleId;

@Repository
public class UserRoleRepositoryImpl extends JdbcCommonDao implements UserRoleRespositoryCustom  {

	

	@Override
	public void saveGroupTeamUserRole(Long userId,List<Integer> roleIds ) throws Exception {

		for(Integer role:roleIds) {
			UserRoleUserIdRoleId ur=new UserRoleUserIdRoleId();
			ur.setUserId(userId);
			ur.setRoleId(role);
			
			UserRoleEntity entity =new UserRoleEntity();
			entity.setUserIdRoleId(ur);
			this.getEm().persist(entity);
		}
		
	}

	@Override
	public void deleteGroupTeamUserRole(Long userId, List<Integer> roleIds) throws Exception {
		for(Integer role:roleIds) {
			UserRoleUserIdRoleId ur=new UserRoleUserIdRoleId();
			ur.setUserId(userId);
			ur.setRoleId(role);
			
			UserRoleEntity entity =new UserRoleEntity();
			entity.setUserIdRoleId(ur);
			UserRoleEntity mentity=this.getEm().merge(entity);
			this.getEm().remove(mentity);
		}
		
	}
	
	

}

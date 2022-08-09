package com.sbmtech.repository;

import java.util.List;

public interface UserRoleRespositoryCustom {
	
	
	public void saveGroupTeamUserRole(Long userId,List<Integer> roleIds) throws Exception ;
	public void deleteGroupTeamUserRole(Long userId,List<Integer> roleIds) throws Exception ;
	
}

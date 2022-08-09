package com.sbmtech.service.impl;

import java.util.List;

import com.sbmtech.common.constant.ExceptionValidationsConstants;
import com.sbmtech.dto.BankDTO;
import com.sbmtech.dto.GroupTeamPersonDTO;
import com.sbmtech.dto.RoleDTO;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.payload.request.GroupRequest;
import com.sbmtech.payload.request.GroupTeamContactRequest;

public class GroupDetailsServiceUtil {

	public static void validateGroupDetialRequest(GroupRequest req) throws Exception {
		ExceptionUtil.throwNullOrEmptyValidationException("GroupId", req.getGroupId(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("GroupName", req.getGroupDetails().getGroupName(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("GroupBranch", req.getGroupDetails().getGroupBranch(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("GroupContactNo", req.getGroupDetails().getGroupContactNo(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("GroupManagerID", req.getGroupDetails().getGroupMgrId(), true);
		
	}
	
	
	public static void validateSaveGroupTeamContactRequest(GroupTeamContactRequest req) throws Exception {
		GroupTeamPersonDTO personDto=req.getGroupTeamPersonDTO();
		if(personDto==null) {
			ExceptionUtil.throwException(ExceptionValidationsConstants.INVALID_PERSON_DATA, ExceptionUtil.EXCEPTION_VALIDATION);
		}
		ExceptionUtil.throwNullOrEmptyValidationException("MemberId", personDto.getMemberId(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("designation", personDto.getDesignation(), true);
		List<RoleDTO> roleList=personDto.getPowerList();
		if(roleList==null || (roleList!=null && roleList.size()==0)) {
			ExceptionUtil.throwException(ExceptionValidationsConstants.INVALID_ROLE_ID, ExceptionUtil.EXCEPTION_VALIDATION);
		}
		if(roleList!=null && roleList.size()!=0){
			for (int i = 0; i < roleList.size(); i++) {
				RoleDTO roleDTO = roleList.get(i);
				ExceptionUtil.throwNullOrEmptyValidationException("(" + (i + 1) + ")"+" RoleId",roleDTO.getRoleId(), true);
			}
		}
		ExceptionUtil.throwNullOrEmptyValidationException("Mobile", personDto.getMobile(), true);
		ExceptionUtil.throwInvalidEmailValException(personDto.getEmail(), true);

		
	}

	
}

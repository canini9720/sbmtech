package com.sbmtech.service.impl;

import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.payload.request.GroupRequest;

public class GroupDetailsServiceUtil {

	public static void validateGroupDetialRequest(GroupRequest req) throws Exception {
		ExceptionUtil.throwNullOrEmptyValidationException("GroupId", req.getGroupId(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("GroupName", req.getGroupDetails().getGroupName(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("GroupBranch", req.getGroupDetails().getGroupBranch(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("GroupContactNo", req.getGroupDetails().getGroupContactNo(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("GroupManagerID", req.getGroupDetails().getGroupMgrId(), true);
		
		
		
	}
	
	
}

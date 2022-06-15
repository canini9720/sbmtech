package com.sbmtech.service.impl;

import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.payload.request.ProfileRequest;

public class CustomeUserDetailsServiceUtil {

	public static void validatePersonalDetialRequest(ProfileRequest req,String action) throws Exception {
		if(action.equals("GET")) {
			ExceptionUtil.throwNullOrEmptyValidationException("UserId", req.getUserId(), true);
		}else if(action.equals("SAVE")){
			//TO DO
		}
		
	}
}

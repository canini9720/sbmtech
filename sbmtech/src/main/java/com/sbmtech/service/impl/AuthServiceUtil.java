package com.sbmtech.service.impl;

import org.apache.commons.lang.StringUtils;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.constant.ExceptionValidationsConstants;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.payload.request.ResetRequest;
import com.sbmtech.payload.request.SignupRequest;
import com.sbmtech.payload.request.VerifyUserRequest;

public class AuthServiceUtil {

	public static VerifyUserRequest validateForgotPwd(VerifyUserRequest req) throws Exception {
		if(StringUtils.isEmpty(req.getUsername())) {
			if(StringUtils.isEmpty(req.getEmail())) {
				ExceptionUtil.throwException(ExceptionValidationsConstants.USERNAME_OR_EMAIL, ExceptionUtil.EXCEPTION_VALIDATION);
			}else {
				ExceptionUtil.throwInvalidEmailValException(req.getEmail(), true);
				req.setType(CommonConstants.INT_TWO); //email
			}
		}else {
			req.setType(CommonConstants.INT_ONE);//username
		}
		return req;
	}
	
	public static void validateReset(ResetRequest req) throws Exception {
		ExceptionUtil.throwNullOrEmptyValidationException("Password ", req.getPassword(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("Confirm Password ", req.getConfirmPassword(), true);
		if(!req.getPassword().equals(req.getConfirmPassword())) {
			ExceptionUtil.throwException(ExceptionValidationsConstants.PASSWORD_NOT_MATCH, ExceptionUtil.EXCEPTION_VALIDATION);
		}
	}
			
}

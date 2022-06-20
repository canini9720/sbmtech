package com.sbmtech.service.impl;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.constant.ExceptionValidationsConstants;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.payload.request.ResetRequest;
import com.sbmtech.payload.request.SignupRequest;
import com.sbmtech.payload.request.VerifyUserRequest;

public class AuthServiceUtil {

	public static void validateSignUp(SignupRequest req) throws Exception {
		Integer memArray[] = { 1,2,3 };
		ExceptionUtil.throwNullOrEmptyValidationException("username ", req.getUsername(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("Password ", req.getPassword(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("Firstname ", req.getFirstname(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("Member Category ", req.getMemberCategory(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("Confirm Password ", req.getConfirmPassword(), true);
		ExceptionUtil.throwInvalidEmailValException(req.getEmail(), true);
		if(!req.getPassword().equals(req.getConfirmPassword())) {
			ExceptionUtil.throwException(ExceptionValidationsConstants.PASSWORD_NOT_MATCH, ExceptionUtil.EXCEPTION_VALIDATION);
		}
		if(!Arrays.asList(memArray).contains(req.getMemberCategory())) {
			ExceptionUtil.throwException(ExceptionValidationsConstants.INVALID_MEMBER_CATEGORY, ExceptionUtil.EXCEPTION_VALIDATION);
		}
	}
	
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
	
	public static ResetRequest validateReset(ResetRequest req) throws Exception {
		ExceptionUtil.throwNullOrEmptyValidationException("encrypedId", req.getEncrypedId(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("verificationId", req.getVerificationId(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("otpCode", req.getOtpCode(), true);
		
		ExceptionUtil.throwNullOrEmptyValidationException("Password ", req.getPassword(), true);
		ExceptionUtil.throwNullOrEmptyValidationException("Confirm Password ", req.getConfirmPassword(), true);
		if(!req.getPassword().equals(req.getConfirmPassword())) {
			ExceptionUtil.throwException(ExceptionValidationsConstants.PASSWORD_NOT_MATCH, ExceptionUtil.EXCEPTION_VALIDATION);
		}
		return req;
	}
			
}

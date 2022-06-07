package com.sbmtech.service.impl;

import com.sbmtech.exception.ExceptionUtil;


public class OTPServiceImplUtil {
	
	
	public static void validateRequest(Long verificationId,Integer otpCode) throws Exception {
		
		ExceptionUtil.throwNullOrEmptyValidationException("verificationId", verificationId, true);
		ExceptionUtil.throwNullOrEmptyValidationException("otpCode", otpCode, true);
	}





}

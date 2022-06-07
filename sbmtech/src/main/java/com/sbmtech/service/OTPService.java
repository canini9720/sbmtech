package com.sbmtech.service;

import com.sbmtech.dto.OtpDTO;

public interface OTPService {
	
	public OtpDTO sendOTP(Long userId,String email,String flowType)throws Exception;
	public Boolean validateOTP(Long verificationId,Integer otpCode)throws Exception;

}

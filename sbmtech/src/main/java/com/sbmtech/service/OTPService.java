package com.sbmtech.service;

import com.sbmtech.dto.OtpDTO;

public interface OTPService {
	
	public OtpDTO sendOTP(Long userId,String email)throws Exception;

}

package com.sbmtech.repository;

import java.util.Optional;

import com.sbmtech.dto.OtpDTO;

public interface OTPRespositoryCustom {
	
	Optional<OtpDTO> saveOtp(Long userId, Integer otpCode, String email,String flowType) throws Exception;
	Boolean validateOtp(Long verificationId,Integer otpCode) throws Exception;
	
}

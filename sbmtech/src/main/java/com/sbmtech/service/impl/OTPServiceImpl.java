package com.sbmtech.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.repository.OTPRepository;
import com.sbmtech.service.OTPService;

@Service
@Transactional
public class OTPServiceImpl implements OTPService {
	
	@Autowired
	OTPRepository otpRepository;

	@Override
	public OtpDTO sendOTP(Long userId,String email) throws Exception {
		Optional<OtpDTO> otp=otpRepository.saveOtp(userId, 12345,  email,CommonConstants.FLOW_TYPE);
		return otp.get();

	}

}

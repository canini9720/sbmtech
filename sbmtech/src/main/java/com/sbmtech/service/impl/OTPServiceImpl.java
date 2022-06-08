package com.sbmtech.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.common.util.OTPGenerator;
import com.sbmtech.dto.NotifEmailDTO;
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.repository.OTPRepository;
import com.sbmtech.service.EmailService;
import com.sbmtech.service.NotificationService;
import com.sbmtech.service.OTPService;

@Service
@Transactional
@DependsOn("AppSystemProp")
public class OTPServiceImpl implements OTPService {
	
	@Autowired
	OTPRepository otpRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	NotificationService notificationService;
	
	
	

	@Override
	public OtpDTO sendOTP(Long userId,String email,String flowType) throws Exception {
		Long smsCode=OTPGenerator.getCode();
		Optional<OtpDTO> otp=otpRepository.saveOtp(userId, CommonUtil.getIntValofObject(smsCode),  email,flowType);
		
		NotifEmailDTO dto=new NotifEmailDTO();
		dto.setEmailTo("ashrafsnj@gmail.com");
		dto.setSubject("OTP for verfification");
		dto.setOtpCode(smsCode);
		dto.setCustomerName(email);
		
		notificationService.sendOTPEmail(dto);
		
		return otp.get();

	}




	@Override
	public Boolean validateOTP(Long verificationId,Integer otpCode) throws Exception {
		return otpRepository.validateOtp(verificationId, otpCode);

	}

}

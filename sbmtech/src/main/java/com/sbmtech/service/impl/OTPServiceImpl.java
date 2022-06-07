package com.sbmtech.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.common.util.OTPGenerator;
import com.sbmtech.dto.NotificationEmailSenderDTO;
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.repository.OTPRepository;
import com.sbmtech.service.EmailService;
import com.sbmtech.service.OTPService;

@Service
@Transactional
@DependsOn("AppSystemProp")
public class OTPServiceImpl implements OTPService {
	
	@Autowired
	OTPRepository otpRepository;
	
	@Autowired
	EmailService emailService;
	
	
	

	@Override
	public OtpDTO sendOTP(Long userId,String email,String flowType) throws Exception {
		Long smsCode=OTPGenerator.getCode();
		Optional<OtpDTO> otp=otpRepository.saveOtp(userId, CommonUtil.getIntValofObject(smsCode),  email,flowType);
		
		NotificationEmailSenderDTO dto=new NotificationEmailSenderDTO();
		dto.setEmailTo("ashrafsnj@gmail.com");
		dto.setSubject("OTP for verfification");
		dto.setEmailBody("OTP for verification  "+smsCode);
		//emailService.testEmail("ashrafsnj@gmail.com", "test from conif2", "body");
		//emailService.sendPlainTextEmail("subject","body");
		emailService.sendEmailWithMultiAttachments(dto)	;
		return otp.get();

	}




	@Override
	public Boolean validateOTP(Long verificationId,Integer otpCode) throws Exception {
		return otpRepository.validateOtp(verificationId, otpCode);

	}

}

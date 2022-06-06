package com.sbmtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.model.User;
import com.sbmtech.payload.response.MessageResponse;
import com.sbmtech.payload.response.OtpResponse;
import com.sbmtech.security.services.CustomeUserDetailsService;
import com.sbmtech.service.EmailService;
import com.sbmtech.service.OTPService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/otp")
public class OTPController {
	
	 
	@Value("${secret.key}")
	private String secretKey;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	CustomeUserDetailsService userDetailsService;

	@PostMapping("sendOTP")
	//@PreAuthorize("hasRole('MEMBER') or hasRole('GROUP') or hasRole('COMPANY') or hasRole('ADMIN')")
	public ResponseEntity<?> sendOTP(@RequestBody String request)throws Exception {
		String userIdStr=CommonUtil.decrypt(CommonUtil.getStringValFromJSONString(request, "userId"),secretKey);
		User user=userDetailsService.getUserById(CommonUtil.getLongValofObject(userIdStr));
		String email=user.getEmail();
		System.out.println("email="+email);
		//emailService.sendEmailWithMultiAttachments(null)
		OtpDTO otp=otpService.sendOTP(user.getUserId(),user.getEmail());
		OtpResponse otpResp=null;
		if(otp!=null) {
			otpResp=new OtpResponse(CommonConstants.SUCCESS_CODE);
			otpResp.setEmail(otp.getEmail());
			otpResp.setResponseMessage("OTP sent to above emailId");
			return ResponseEntity.ok(otpResp);
		}else {
			otpResp=new OtpResponse(CommonConstants.FAILURE_CODE);
			return ResponseEntity.badRequest().body(otpResp);
		}
		
	}
}

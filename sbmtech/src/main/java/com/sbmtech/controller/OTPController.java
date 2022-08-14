package com.sbmtech.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.OtpRequest;
import com.sbmtech.payload.request.ValidateOtpRequest;
import com.sbmtech.payload.request.VerifyUserRequest;
import com.sbmtech.security.services.CustomeUserDetailsService;
import com.sbmtech.service.OTPService;
import com.sbmtech.service.impl.AppSystemPropImpl;
import com.sbmtech.service.impl.OTPServiceImplUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@DependsOn("AppSystemProp")
@RestController
@RequestMapping("/api/otp")
public class OTPController {

	//@Value("${secret.key}")
	private String secretKey;

	@Autowired
	OTPService otpService;

	@Autowired
	CustomeUserDetailsService userDetailsService;
	
	@PostConstruct
	public void initialize() throws GeneralSecurityException, IOException {
		secretKey = AppSystemPropImpl.props.get("json.secretKey");
	}

	@PostMapping(value="sendOTP", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	public String sendOTP(@RequestBody OtpRequest otpRequest) throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		String userIdStr = CommonUtil.decrypt(otpRequest.getUserId(), secretKey);
		User user = userDetailsService.getUserById(CommonUtil.getLongValofObject(userIdStr));
		if (user != null) {
			OtpDTO otp = otpService.sendOTP(user.getUserId(), user.getEmail(),CommonConstants.FLOW_TYPE_REGISTRATION);
			if (otp != null) {
				respObj.put("message", "OTP sent to above emailId");
				respObj.put("verificationId", otp.getVerificationId());
				respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
				respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
			}
		}else{
			respObj.put("message", "User is not matched");
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);

	}

	@PostMapping(value="validateOTP", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	public String validateOTP(@RequestBody ValidateOtpRequest otpRequest) throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		Long verificationId=otpRequest.getVerificationId();
		Integer otpCode=otpRequest.getUserOtp();
		OTPServiceImplUtil.validateRequest(verificationId, otpCode);
		if(otpService.validateOTP(verificationId,otpCode)) {
			respObj.put("memberCat", otpService.getUserType(verificationId));
			respObj.put("message", "OTP validation is Success");
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		}else {
			respObj.put("message", "OTP validation is failed");
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));

		}
		 return gson.toJson(respObj);

	}
}

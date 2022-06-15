package com.sbmtech.controller;


import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.payload.request.ProfileRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.payload.response.ProfileResponse;
import com.sbmtech.repository.GDriveUserRepository;
import com.sbmtech.repository.RoleRepository;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.security.jwt.JwtUtils;
import com.sbmtech.security.services.CustomeUserDetailsService;
import com.sbmtech.security.services.RefreshTokenService;
import com.sbmtech.service.CommonService;
import com.sbmtech.service.EmailService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/userProfile")
public class ProfileController {
	
	private static final Logger loggerInfo = Logger.getLogger(CommonConstants.LOGGER_SERVICES_INFO);
	
	@Value("${secret.key}")
	private String secretKey;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GDriveUserRepository gdriveRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	
	RefreshTokenService refreshTokenService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	CustomeUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtils jwtUtils;
	
	
	
	
	@PostMapping(value="getPersonalDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole('MEMBER')  or hasRole('GROUP')  or hasRole('COMPANY')  or hasRole('ADMIN')")
	public String getPersonalDetails(@RequestBody ProfileRequest profileRequest) throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		
		ProfileResponse resp = userDetailsService.getUserPersonalContactById(profileRequest);
		if (resp != null) {
			respObj.put("userDetail", resp);
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	 }
	
	
	@PostMapping(value="savePersonalDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole('MEMBER')  or hasRole('GROUP')  or hasRole('COMPANY')  or hasRole('ADMIN')")
	public String savePersonalDetails(@RequestBody ProfileRequest profileRequest)throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		
		CommonResponse resp = userDetailsService.savePersonalDetails(profileRequest);
		if (resp != null) {
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	  }
	
	
	
}
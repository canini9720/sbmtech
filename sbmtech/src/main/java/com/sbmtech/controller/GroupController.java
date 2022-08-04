package com.sbmtech.controller;


import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.dto.ContactDetailDTO;
import com.sbmtech.dto.DocumentDetailDTO;
import com.sbmtech.dto.GroupDetailDTO;
import com.sbmtech.payload.request.DocumentRequest;
import com.sbmtech.payload.request.GroupRequest;
import com.sbmtech.payload.request.ProfileRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.repository.GDriveUserRepository;
import com.sbmtech.repository.RoleRepository;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.security.jwt.JwtUtils;
import com.sbmtech.security.services.GroupDetailsService;
import com.sbmtech.security.services.RefreshTokenService;
import com.sbmtech.security.services.UserDetailsImpl;
import com.sbmtech.service.CommonService;
import com.sbmtech.service.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/group/userProfile")
public class GroupController {
	
	private static final Logger loggerInfo = Logger.getLogger(CommonConstants.LOGGER_SERVICES_INFO);
	
	
	
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
	GroupDetailsService groupDetailsService;
	
	@Autowired
	JwtUtils jwtUtils;
	
	
	
	
	@PostMapping(value="saveGroupDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.group)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String saveGroupDetails(@RequestBody GroupRequest groupRequest,
			@CurrentSecurityContext(expression = "authentication")  Authentication authentication)throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		UserDetailsImpl customUser = (UserDetailsImpl)authentication.getPrincipal();
		Long userId= customUser.getUserId();
		groupRequest.setGroupId(userId);
		CommonResponse resp = groupDetailsService.saveGroupDetails(groupRequest);
		if (resp != null) {
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	}
	
	
	@GetMapping(value="getGroupDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.group) ")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String  getGroupDetails(@CurrentSecurityContext(expression = "authentication")  Authentication authentication) throws Exception {
		Gson gson = new Gson();
		UserDetailsImpl customUser = (UserDetailsImpl)authentication.getPrincipal();
		Long userId= customUser.getUserId();
		JSONObject respObj = new JSONObject();
		GroupRequest groupRequest=new GroupRequest();
		groupRequest.setGroupId(userId);
		GroupDetailDTO resp = groupDetailsService.getGroupDetailsById(groupRequest);
		if (resp != null) {
			respObj.put("groupDetails", resp);
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	 }

	@PostMapping(value="saveGroupContactDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.group) ")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String saveGroupContactDetails(@RequestBody GroupRequest groupRequest,
			@CurrentSecurityContext(expression = "authentication")  Authentication authentication)throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		UserDetailsImpl customUser = (UserDetailsImpl)authentication.getPrincipal();
		Long userId= customUser.getUserId();
		groupRequest.setGroupId(userId);
		CommonResponse resp = groupDetailsService.saveGroupContactDetails(groupRequest);
		if (resp != null) {
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	}
	
	
	@GetMapping(value="getGroupContactDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.group) ")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String  getGroupContactDetails(@CurrentSecurityContext(expression = "authentication")  Authentication authentication) throws Exception {
		Gson gson = new Gson();
		UserDetailsImpl customUser = (UserDetailsImpl)authentication.getPrincipal();
		Long userId= customUser.getUserId();
		JSONObject respObj = new JSONObject();
		GroupRequest groupRequest=new GroupRequest();
		groupRequest.setGroupId(userId);
		ContactDetailDTO resp = groupDetailsService.getGroupContactDetails(groupRequest);
		if (resp != null) {
			respObj.put("contactDetails", resp);
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	 }
	
	@PostMapping(value="saveGroupDocumentDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.group) ")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String saveGroupDocumentDetails(@RequestBody DocumentRequest docRequest,
			@CurrentSecurityContext(expression = "authentication")  Authentication authentication)throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		UserDetailsImpl customUser = (UserDetailsImpl)authentication.getPrincipal();
		Long userId= customUser.getUserId();
		docRequest.setUserId(userId);
		CommonResponse resp =null;
		resp=groupDetailsService.saveGroupDocumentDetails(docRequest);
		if (resp != null) {
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	}
	
	
	@GetMapping(value="getGroupDocumentDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.group) ")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String  getGroupDocumentDetails(@CurrentSecurityContext(expression = "authentication")  Authentication authentication) throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		GroupRequest groupRequest=new GroupRequest();

		UserDetailsImpl customUser = (UserDetailsImpl)authentication.getPrincipal();
		Long userId= customUser.getUserId();
		groupRequest.setGroupId(userId);
		DocumentDetailDTO resp = groupDetailsService.getGroupDocumentDetails(groupRequest);
		if (resp != null) {
			respObj.put("documentDetailDTO", resp);
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	}
	
	
	
}
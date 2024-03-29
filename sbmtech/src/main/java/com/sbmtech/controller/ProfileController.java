package com.sbmtech.controller;


import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.dto.ContactDetailDTO;
import com.sbmtech.dto.PersonDetailDTO;
import com.sbmtech.dto.UserRegistrationDetailDTO;
import com.sbmtech.payload.request.DocumentRequest;
import com.sbmtech.payload.request.ProfileRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.payload.response.MemberDetailResponse;
import com.sbmtech.payload.response.MemberRegDetailResponse;
import com.sbmtech.repository.GDriveUserRepository;
import com.sbmtech.repository.RoleRepository;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.security.jwt.JwtUtils;
import com.sbmtech.security.services.CustomeUserDetailsService;
import com.sbmtech.security.services.RefreshTokenService;
import com.sbmtech.service.CommonService;
import com.sbmtech.service.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/userProfile")
public class ProfileController {
	
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
	CustomeUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtils jwtUtils;
	
	
	@GetMapping(value="getMemberRegistrationDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.member)   or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String getUserRegistrationDetails(@RequestParam(name = "userId", required = true) Long userId) throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		ProfileRequest profileRequest=new ProfileRequest();
		profileRequest.setUserId(userId);
		UserRegistrationDetailDTO resp = userDetailsService.getMemberRegistrationDetailsById(profileRequest);
		if (resp != null) {
			resp.setVerified(null);
			resp.setEnabled(null);
			respObj.put("memberRegistrationDetailDTO", resp);
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	 }
	
	@GetMapping(value="getMemberPersonalDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.member)   or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String  getMemberPersonalDetails(@RequestParam(name = "userId", required = true) Long userId) throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		ProfileRequest profileRequest=new ProfileRequest();
		profileRequest.setUserId(userId);
		PersonDetailDTO resp = userDetailsService. getMemberPersonalDetailsById(profileRequest);
		if (resp != null) {
			respObj.put("personDetails", resp);
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	 }
	
	@GetMapping(value="getMemberContactlDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.member)   or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String  getMemberContactlDetails(@RequestParam(name = "userId", required = true) Long userId) throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		ProfileRequest profileRequest=new ProfileRequest();
		profileRequest.setUserId(userId);
		ContactDetailDTO resp = userDetailsService.getMemberContactDetailsById(profileRequest);
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
	
	
	@PostMapping(value="saveMemberPersonalDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.member)   or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String savePersonalDetails(@RequestBody ProfileRequest profileRequest)throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		
		CommonResponse resp = userDetailsService.saveMemberPersonalDetails(profileRequest);
		if (resp != null) {
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	  }
	
	
	@PostMapping(value="saveMemberContactDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.member)   or hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public String saveMemberContactDetails(@RequestBody ProfileRequest profileRequest)throws Exception {
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		
		CommonResponse resp = userDetailsService.saveMemberContactDetails(profileRequest);
		if (resp != null) {
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
	  }
	
	
	
	@GetMapping(value="getAllMemberDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public String getAllMemberDetails(
            @RequestParam(value = "pageNo", defaultValue = CommonConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = CommonConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = CommonConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = CommonConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		MemberDetailResponse resp= userDetailsService.getAllMemberDetails(pageNo, pageSize, sortBy, sortDir);
		if (resp != null) {
			respObj.put("getAllMemberDetails", resp);
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put("getAllMemberDetails", resp);
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
    }
	
	@GetMapping(value="getAllMemberRegDetails", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	@PreAuthorize("hasRole(@securityService.admin)")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public String getAllMemberRegDetails(
            @RequestParam(value = "pageNo", defaultValue = CommonConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = CommonConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = CommonConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = CommonConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		MemberRegDetailResponse resp= userDetailsService.getAllMemberRegDetails(pageNo, pageSize, sortBy, sortDir);
		if (resp != null) {
			respObj.put("getAllMemberRegDetails", resp);
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		
		}else{
			respObj.put("getAllMemberRegDetails", resp);
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		  return gson.toJson(respObj);
    }
	
	
	
	
}
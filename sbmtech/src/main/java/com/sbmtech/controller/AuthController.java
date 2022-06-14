package com.sbmtech.controller;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.model.ERole;
import com.sbmtech.model.GDriveUser;
import com.sbmtech.model.RefreshToken;
import com.sbmtech.model.Role;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.LoginRequest;
import com.sbmtech.payload.request.ResetRequest;
import com.sbmtech.payload.request.SignupRequest;
import com.sbmtech.payload.request.TokenRefreshRequest;
import com.sbmtech.payload.request.VerifyUserRequest;
import com.sbmtech.payload.response.CommonRespone;
import com.sbmtech.payload.response.JwtResponse;
import com.sbmtech.payload.response.SignupResponse;
import com.sbmtech.payload.response.TokenRefreshResponse;
import com.sbmtech.repository.GDriveUserRepository;
import com.sbmtech.repository.RoleRepository;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.security.jwt.JwtUtils;
import com.sbmtech.security.jwt.exception.TokenRefreshException;
import com.sbmtech.security.services.CustomeUserDetailsService;
import com.sbmtech.security.services.RefreshTokenService;
import com.sbmtech.security.services.UserDetailsImpl;
import com.sbmtech.service.CommonService;
import com.sbmtech.service.EmailService;
import com.sbmtech.service.impl.AuthServiceUtil;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
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
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)throws Exception {
	    Authentication authentication = authenticationManager
	        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	    userDetailsService.isVerified(userDetails);

	    String jwt = jwtUtils.generateJwtToken(userDetails);
	    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
	        .collect(Collectors.toList());
	    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
	    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
	        userDetails.getUsername(), roles));
	  }
	
	
	
	  @PostMapping("/refreshtoken")
	  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
	    String requestRefreshToken = request.getRefreshToken();
	    return refreshTokenService.findByToken(requestRefreshToken)
	        .map(refreshTokenService::verifyExpiration)
	        .map(RefreshToken::getUser)
	        .map(user -> {
	          String token = jwtUtils.generateTokenFromUsername(user.getUsername());
	          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
	        })
	        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
	            "Refresh token is not in database!"));
	  }
	  
	  
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest)throws Exception {
		Boolean isVerified=true;
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			CommonRespone resp=new CommonRespone(CommonConstants.FAILURE_CODE);
			resp.setResponseMessage("Error: Username is already taken!");
			return ResponseEntity.badRequest().body(resp);
		}
		isVerified=userDetailsService.isVerifiedByEmail(signUpRequest.getEmail(),true);
		
		if (userRepository.existsByEmail(signUpRequest.getEmail())  && isVerified) {
			CommonRespone resp=new CommonRespone(CommonConstants.FAILURE_CODE);
			resp.setResponseMessage("Error: email is already taken");
			return ResponseEntity.badRequest().body(resp);
		}
		
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getFirstname(),
							 signUpRequest.getLastname(),
							 signUpRequest.getMemberCategory(),
							 encoder.encode(signUpRequest.getPassword()),
							 signUpRequest.getEmail());
		Set<String> strRoles = new HashSet<>();
		Set<Role> roles = new HashSet<>();
		if(signUpRequest.getMemberCategory()==CommonConstants.INT_ONE_MEMBER) {
			strRoles.add("member");
		}else if(signUpRequest.getMemberCategory()==CommonConstants.INT_TWO_GROUP) {
			strRoles.add("group");
		}else if(signUpRequest.getMemberCategory()==CommonConstants.INT_THREE_COMPNAY) {
			strRoles.add("company");
		}else {
			strRoles.add("admin");
		}
		
		strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					List<Role> adminRole = roleRepository.findAll();
					roles.addAll(adminRole);
					break;
				case "member":
					Role memRole = roleRepository.findByName(ERole.ROLE_MEMBER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(memRole);
					break;
				case "group":
					Role groupRole = roleRepository.findByName(ERole.ROLE_GROUP)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(groupRole);
					break;
				case "company":
					Role companyRole = roleRepository.findByName(ERole.ROLE_COMPANY)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(companyRole);
					break;	
				default:
					Role memDefRole = roleRepository.findByName(ERole.ROLE_MEMBER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(memDefRole);
				}
			});
		
		user.setRoles(roles);
		user.setEnabled(true);
		user.setVerified(false);
		User dbUser=userRepository.save(user);
		if(dbUser!=null) {
			String parentId=commonService.createUserFolder(String.valueOf(dbUser.getUserId()));
			GDriveUser gdriveUser=new GDriveUser();
			gdriveUser.setUserId(dbUser.getUserId());;
			gdriveUser.setParentId(parentId);
			gdriveRepository.save(gdriveUser);
			
			
		}
		SignupResponse resp=new SignupResponse(CommonConstants.SUCCESS_CODE);
		resp.setResponseMessage(CommonConstants.SUCCESS_DESC);
		resp.setResponseDesc("Proceed validate OTP to complete Registration Process");
		resp.setUserId(CommonUtil.encrypt(CommonUtil.getStringValofObject(dbUser.getUserId()),secretKey));
		return ResponseEntity.ok(resp);
	}
	
	
	@PostMapping(value="verifyUser", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	public String verifyUser(@RequestBody VerifyUserRequest forgotRequest)throws Exception {
		forgotRequest = AuthServiceUtil.validateForgotPwd(forgotRequest);
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		OtpDTO otp= userDetailsService.forgotPwd(forgotRequest);
		if(otp!=null) {
			respObj.put("message", "OTP sent to above emailId");
			respObj.put("verificationId", otp.getId());
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		}else{
			respObj.put("message", "User is not Found");
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		return gson.toJson(respObj);
	}
	
	
	@PostMapping(value="reset", produces=MediaType.APPLICATION_JSON_VALUE+CommonConstants.CHARSET_UTF8)
	public String reset(@RequestBody ResetRequest req)throws Exception {
		AuthServiceUtil.validateReset(req);
		 encoder.encode(req.getPassword());
		Gson gson = new Gson();
		JSONObject respObj = new JSONObject();
		OtpDTO otp=null;// userDetailsService.forgotPwd(forgotRequest);
		if(otp!=null) {
			respObj.put("message", "OTP sent to above emailId");
			respObj.put("verificationId", otp.getId());
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.SUCCESS_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.SUCCESS_CODE));
		}else{
			respObj.put("message", "User is not Found");
			respObj.put(CommonConstants.RESPONSE_CODE, CommonConstants.FAILURE_CODE);
			respObj.put(CommonConstants.RESPONSE_DESC, CommonUtil.getSuccessOrFailureMessageWithId(CommonConstants.FAILURE_CODE));
		}
		return gson.toJson(respObj);
	}
}
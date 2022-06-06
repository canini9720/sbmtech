package com.sbmtech.controller;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.model.ERole;
import com.sbmtech.model.RefreshToken;
import com.sbmtech.model.Role;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.LoginRequest;
import com.sbmtech.payload.request.SignupRequest;
import com.sbmtech.payload.request.TokenRefreshRequest;
import com.sbmtech.payload.response.CommonRespone;
import com.sbmtech.payload.response.JwtResponse;
import com.sbmtech.payload.response.MessageResponse;
import com.sbmtech.payload.response.SignupResponse;
import com.sbmtech.payload.response.TokenRefreshResponse;
import com.sbmtech.repository.RoleRepository;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.security.jwt.JwtUtils;
import com.sbmtech.security.jwt.exception.TokenRefreshException;
import com.sbmtech.security.services.RefreshTokenService;
import com.sbmtech.security.services.UserDetailsImpl;
import com.sbmtech.service.EmailService;
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
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	
	RefreshTokenService refreshTokenService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	    Authentication authentication = authenticationManager
	        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

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
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			CommonRespone resp=new CommonRespone(CommonConstants.FAILURE_CODE);
			resp.setResponseMessage("Error: Username is already taken!");
			return ResponseEntity.badRequest().body(resp);
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
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
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
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
		user.setEnabled(false);
		User dbUser=userRepository.save(user);
		SignupResponse resp=new SignupResponse(CommonConstants.SUCCESS_CODE);
		//resp.setResponseCode(CommonConstants.SUCCESS_CODE);
		//resp.setResponseStatus(CommonConstants.SUCCESS_DESC);
		resp.setResponseDesc("Proceed validate OTP to complete Registration Process");
		resp.setUserId(CommonUtil.encrypt(CommonUtil.getStringValofObject(dbUser.getUserId()),secretKey));
		return ResponseEntity.ok(resp);
	}
}
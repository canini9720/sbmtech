package com.sbmtech.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sbmtech.dto.OtpDTO;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.ProfileRequest;
import com.sbmtech.payload.request.VerifyUserRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.payload.response.ProfileResponse;

public interface CustomeUserDetailsService extends UserDetailsService {

	public User getUserById(Long userId) throws Exception;
	public void isVerified(UserDetailsImpl userDetails ) throws Exception ;
	public OtpDTO forgotPwd(VerifyUserRequest forgotRequest ) throws Exception ;
	public boolean isVerifiedByEmail(String email,Boolean verified) throws Exception ;
	public ProfileResponse getUserPersonalContactById( ProfileRequest profileRequest) throws Exception;
	public CommonResponse savePersonalDetails( ProfileRequest profileRequest) throws Exception;
	
	
	
}

package com.sbmtech.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sbmtech.dto.OtpDTO;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.VerifyUserRequest;

public interface CustomeUserDetailsService extends UserDetailsService {

	public User getUserById(Long userId) throws Exception;
	public void isVerified(UserDetailsImpl userDetails ) throws Exception ;
	public OtpDTO forgotPwd(VerifyUserRequest forgotRequest ) throws Exception ;
	public boolean isVerifiedByEmail(String email,Boolean verified) throws Exception ;
	
	
}

package com.sbmtech.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sbmtech.model.User;
import com.sbmtech.payload.request.GroupRequest;
import com.sbmtech.payload.response.CommonResponse;

public interface GroupDetailsService extends UserDetailsService {

	public User getUserById(Long userId) throws Exception;
	public CommonResponse saveGroupDetails( GroupRequest groupRequest) throws Exception;
	


}

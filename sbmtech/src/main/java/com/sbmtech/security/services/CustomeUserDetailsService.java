package com.sbmtech.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sbmtech.model.User;

public interface CustomeUserDetailsService extends UserDetailsService {

	public User getUserById(Long userId) throws Exception;
}

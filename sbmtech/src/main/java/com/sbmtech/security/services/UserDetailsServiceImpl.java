package com.sbmtech.security.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbmtech.common.constant.ExceptionBusinessConstants;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.model.User;
import com.sbmtech.repository.UserRepository;
@Service
@Transactional
public class UserDetailsServiceImpl implements CustomeUserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(user);
	}
	

	public void isVerified(UserDetailsImpl user ) throws Exception {
		if(!user.isVerified()) {
			ExceptionUtil.throwException(ExceptionBusinessConstants.USER_IS_NOT_VERIFIED, ExceptionUtil.EXCEPTION_BUSINESS);
		}
		
	}


	@Override
	public User getUserById(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("UserId Not Found: " + userId));
        return user;
	}
}
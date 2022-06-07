package com.sbmtech.security.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.constant.ExceptionBusinessConstants;
import com.sbmtech.common.constant.ExceptionValidationsConstants;
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.VerifyUserRequest;
import com.sbmtech.payload.response.CommonRespone;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.service.OTPService;
@Service
@Transactional
public class UserDetailsServiceImpl implements CustomeUserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OTPService otpService;
	
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


	@Override
	public OtpDTO forgotPwd(VerifyUserRequest forgotRequest) throws Exception {
		OtpDTO otp=null;
		Optional<User> user=null;
		if((forgotRequest.getType()==CommonConstants.INT_ONE) ) {
			if(!userRepository.existsByUsername(forgotRequest.getUsername())) {
				ExceptionUtil.throwException(ExceptionValidationsConstants.USERNAME_OR_EMAIL, ExceptionUtil.EXCEPTION_VALIDATION);
			}
			user = userRepository.findByUsername(forgotRequest.getUsername());
		}
		if((forgotRequest.getType()==CommonConstants.INT_TWO) ) {
			if(!userRepository.existsByEmail(forgotRequest.getEmail())) {
				ExceptionUtil.throwException(ExceptionValidationsConstants.USERNAME_OR_EMAIL, ExceptionUtil.EXCEPTION_VALIDATION);
			}
			user = userRepository.findByEmail(forgotRequest.getEmail());
		}
		otp=otpService.sendOTP(user.get().getUserId(), user.get().getEmail(),CommonConstants.FLOW_TYPE_FORGETPWD);
		
		
		return otp;
		
	}
}
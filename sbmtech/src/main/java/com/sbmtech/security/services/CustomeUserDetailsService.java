package com.sbmtech.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sbmtech.dto.ContactDetailDTO;
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.dto.PersonDetailDTO;
import com.sbmtech.dto.UserRegistrationDetailDTO;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.ProfileRequest;
import com.sbmtech.payload.request.VerifyUserRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.payload.response.MemberDetailResponse;
import com.sbmtech.payload.response.ProfileResponse;

public interface CustomeUserDetailsService extends UserDetailsService {

	public User getUserById(Long userId) throws Exception;
	public void isVerified(UserDetailsImpl userDetails ) throws Exception ;
	public OtpDTO forgotPwd(VerifyUserRequest forgotRequest ) throws Exception ;
	public boolean isVerifiedByEmail(String email,Boolean verified) throws Exception ;
	public ProfileResponse getUserPersonalContactById( ProfileRequest profileRequest) throws Exception;
	public CommonResponse savePersonalDetails( ProfileRequest profileRequest) throws Exception;
	public MemberDetailResponse getAllMemberDetails(int pageNo, int pageSize, String sortBy, String sortDir);
	
	public UserRegistrationDetailDTO getMemberRegistrationDetailsById( ProfileRequest profileRequest) throws Exception;
	public PersonDetailDTO getMemberPersonalDetailsById( ProfileRequest profileRequest) throws Exception;
	public ContactDetailDTO getMemberContactDetailsById( ProfileRequest profileRequest) throws Exception;
	
	public CommonResponse saveMemberPersonalDetails( ProfileRequest profileRequest) throws Exception;
	public CommonResponse saveMemberContactDetails( ProfileRequest profileRequest) throws Exception;
	
}

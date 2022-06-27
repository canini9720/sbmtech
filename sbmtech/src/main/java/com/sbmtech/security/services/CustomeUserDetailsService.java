package com.sbmtech.security.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sbmtech.dto.ContactDetailDTO;
import com.sbmtech.dto.DocumentDetailDTO;
import com.sbmtech.dto.EducationDetailDTO;
import com.sbmtech.dto.EmploymentDetailDTO;
import com.sbmtech.dto.JobRequestDetailDTO;
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.dto.PersonDetailDTO;
import com.sbmtech.dto.ProfileCompleteStatusDTO;
import com.sbmtech.dto.UserRegistrationDetailDTO;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.DocumentRequest;
import com.sbmtech.payload.request.EduRequest;
import com.sbmtech.payload.request.EmploymentRequest;
import com.sbmtech.payload.request.JobRequest;
import com.sbmtech.payload.request.ProfileRequest;
import com.sbmtech.payload.request.ResetRequest;
import com.sbmtech.payload.request.VerifyUserRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.payload.response.MemberDetailResponse;
import com.sbmtech.payload.response.MemberRegDetailResponse;
import com.sbmtech.payload.response.ProfileResponse;

public interface CustomeUserDetailsService extends UserDetailsService {

	public User getUserById(Long userId) throws Exception;
	public void isVerified(User user) throws Exception ;
	public CommonResponse reset(ResetRequest req, String encodedPwd) throws Exception ;
	public OtpDTO verifyUser(VerifyUserRequest req) throws Exception ;
	public boolean isVerifiedByEmail(String email,Boolean verified) throws Exception ;
	public ProfileResponse getUserPersonalContactById( ProfileRequest profileRequest) throws Exception;
	//public CommonResponse savePersonalDetails( ProfileRequest profileRequest) throws Exception;
	public MemberDetailResponse getAllMemberDetails(int pageNo, int pageSize, String sortBy, String sortDir);
	public MemberRegDetailResponse getAllMemberRegDetails(int pageNo, int pageSize, String sortBy, String sortDir);
	public UserRegistrationDetailDTO getMemberRegistrationDetailsById( ProfileRequest profileRequest) throws Exception;
	public PersonDetailDTO getMemberPersonalDetailsById( ProfileRequest profileRequest) throws Exception;
	public ContactDetailDTO getMemberContactDetailsById( ProfileRequest profileRequest) throws Exception;
	
	public CommonResponse saveMemberPersonalDetails( ProfileRequest profileRequest) throws Exception;
	public CommonResponse saveMemberContactDetails( ProfileRequest profileRequest) throws Exception;
	public void deleteMemberContactDetails(List<Long> ids) throws Exception;
	public void deleteDocumentDetails(List<Long> oldDocIds) throws Exception;
	public ProfileCompleteStatusDTO getMemberProfileCompletionStatus(Long userId) throws Exception;
	
	public CommonResponse saveDocumentDetails(DocumentRequest docReq)throws Exception;
	public DocumentDetailDTO getDocumentDetailsById(ProfileRequest profileRequest) throws Exception;
	
	public CommonResponse saveEduDetails(EduRequest eduReq)throws Exception;
	public void deleteEducationDetails(List<Long> oldEduIds) throws Exception ;
	public EducationDetailDTO getMemberEduDetailsById(ProfileRequest profileRequest) throws Exception;
	
	
	public CommonResponse saveEmploymentDetails(EmploymentRequest employmentRequest)throws Exception;
	public void deleteEmploymentDetails(List<Long> oldEmptIds) throws Exception ;
	public EmploymentDetailDTO getMemberEmpDetailsById(ProfileRequest profileRequest) throws Exception;
	
	
	public CommonResponse saveJobRequestDetails(JobRequest jobRequest)throws Exception;	
	public void deleteJobReqDetails(List<Long> oldJobReqIds) throws Exception ;
	public JobRequestDetailDTO getMemberJobReqDetailsById(ProfileRequest profileRequest) throws Exception;

}

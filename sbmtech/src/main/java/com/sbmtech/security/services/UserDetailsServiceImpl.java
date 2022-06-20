package com.sbmtech.security.services;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.constant.ExceptionBusinessConstants;
import com.sbmtech.common.constant.ExceptionValidationsConstants;
import com.sbmtech.dto.ContactDTO;
import com.sbmtech.dto.ContactDetailDTO;
import com.sbmtech.dto.FileItemDTO;
import com.sbmtech.dto.ProfileCompleteStatusDTO;
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.dto.PersonDetailDTO;
import com.sbmtech.dto.UserDetailDTO;
import com.sbmtech.dto.UserRegistrationDetailDTO;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.model.MemberContactDetailEntity;
import com.sbmtech.model.MemberPersonalDetailEntity;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.ProfileRequest;
import com.sbmtech.payload.request.ResetRequest;
import com.sbmtech.payload.request.VerifyUserRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.payload.response.MemberDetailResponse;
import com.sbmtech.payload.response.ProfileResponse;
import com.sbmtech.repository.MemberContactRepository;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.service.CommonService;
import com.sbmtech.service.OTPService;
import com.sbmtech.service.impl.AuthServiceUtil;
import com.sbmtech.service.impl.CustomeUserDetailsServiceUtil;
@Service
@Transactional
public class UserDetailsServiceImpl implements CustomeUserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MemberContactRepository mcRepository;
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	CommonService commonService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(user);
	}
	

	public void isVerified(User user ) throws Exception {
		if(!user.getVerified()) {
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
	public OtpDTO verifyUser(VerifyUserRequest req) throws Exception {
        OtpDTO otp=null;

		Optional<User> user=null;
		if((req.getType()==CommonConstants.INT_ONE) ) {
			if(!userRepository.existsByUsername(req.getUsername())) {
				ExceptionUtil.throwException(ExceptionValidationsConstants.USERNAME_OR_EMAIL, ExceptionUtil.EXCEPTION_VALIDATION);
			}
			user = userRepository.findByUsername(req.getUsername());
		}
		if((req.getType()==CommonConstants.INT_TWO) ) {
			user=userRepository.getUserByEmailAndVerified(req.getEmail(),true);
			if(!user.isPresent()) {
				ExceptionUtil.throwException(ExceptionValidationsConstants.USERNAME_OR_EMAIL, ExceptionUtil.EXCEPTION_VALIDATION);
			}
		}
		if(user.isPresent()) {
	        otp=otpService.sendOTP(user.get().getUserId(), user.get().getEmail(),CommonConstants.FLOW_TYPE_FORGETPWD);
		} 
		return otp;
	}
	@Override
	public CommonResponse reset(ResetRequest req,String encodedPwd) throws Exception {
		AuthServiceUtil.validateReset(req);
		CommonResponse resp=null;
		Optional<User> user=userRepository.findById(req.getUserId());
		boolean isRequested=otpService.isUserRequestedForReset(req.getVerificationId(),req.getUserId());
		if(!otpService.validateOTP(req.getVerificationId(),req.getOtpCode())) {
			ExceptionUtil.throwException(ExceptionValidationsConstants.INVALID_OTP, ExceptionUtil.EXCEPTION_VALIDATION);
		}
		if(user.isPresent() && isRequested) {
			user.get().setPassword(encodedPwd);
			resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
		} 
		return resp;
	}


	@Override
	public boolean isVerifiedByEmail(String email,Boolean verified) throws Exception {
		Optional<User> user= userRepository.getUserByEmailAndVerified(email,verified);
		return user.isPresent();
	}


	@Override
	public ProfileResponse getUserPersonalContactById( ProfileRequest profileRequest) throws Exception {
		ProfileResponse resp=null;
		CustomeUserDetailsServiceUtil.validatePersonalDetialRequest(profileRequest,CommonConstants.GET);
		Optional<User> userOp = userRepository.findById(profileRequest.getUserId());
		if(userOp.isPresent()) {
			resp=new ProfileResponse();
			PersonDetailDTO personalDetailsDTO=new PersonDetailDTO();
			List<ContactDTO> contactDetailsList=new ArrayList<>();
			User user=userOp.get();
		
			MemberPersonalDetailEntity personalDetail=user.getMemberPersonalDetailEntity();
			List<MemberContactDetailEntity> contactList=user.getMemeberConactList();
			
			for(MemberContactDetailEntity contEnt:contactList) {
				if(contEnt.getActive()==CommonConstants.INT_ONE) {
					ContactDTO contactDetailDTO=new ContactDTO();
					BeanUtils.copyProperties(contEnt, contactDetailDTO);
					contactDetailsList.add(contactDetailDTO);
				}
			}
			if(personalDetail!=null) {
				BeanUtils.copyProperties(personalDetail, personalDetailsDTO);
			}
			resp.setUserId(user.getUserId());
			resp.setPersonDetails(personalDetailsDTO);
			resp.setContactDetails(contactDetailsList);
			
			
		}
		return resp;
	}

/*
	@Override
	public CommonResponse savePersonalDetails(ProfileRequest profileRequest) throws Exception {
		CommonResponse resp=null;
		CustomeUserDetailsServiceUtil.validatePersonalDetialRequest(profileRequest,CommonConstants.SAVE);
		User userDb=null;
		PersonDetailDTO personalDetailsDTO=profileRequest.getPersonDetails();
		List<ContactDTO> contactDetailsList=profileRequest.getContactDetails();
		Optional<User> userOp = userRepository.findById(profileRequest.getUserId());
		List<MemberContactDetailEntity>oldContactEntity= null;
		if(userOp.isPresent()) {
			User user=userOp.get();
			oldContactEntity=user.getMemeberConactList();
			oldContactEntity.forEach(o -> o.setActive(CommonConstants.INT_ZERO));
			personalDetailsDTO.setUserId(profileRequest.getUserId());
			
			MemberPersonalDetailEntity personalDetialEntity=new MemberPersonalDetailEntity();
			BeanUtils.copyProperties(personalDetailsDTO,personalDetialEntity);
			user.setMemberPersonalDetailEntity(personalDetialEntity);
			
			
			for(ContactDTO contDet:contactDetailsList) {
				MemberContactDetailEntity contEnt=new MemberContactDetailEntity();
				BeanUtils.copyProperties(contDet, contEnt);
				contEnt.setActive(CommonConstants.INT_ONE);
				contEnt.setUserEntity(user);
				user.addContactDetail(contEnt);
			}
			
			userDb=userRepository.saveAndFlush(user);
			if(userDb!=null ) {
				resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
			}
		}
		return resp;
	}
*/

	@Override
	public MemberDetailResponse getAllMemberDetails(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

		List<UserDetailDTO> userDetailDTO=null;
		MemberDetailResponse memberDetailResponse=new MemberDetailResponse();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> pageUser = userRepository.findByMemberCategoryAndVerified(CommonConstants.INT_ONE,true ,pageable);

        List<User> listOfPosts = pageUser.getContent();
        
        userDetailDTO= listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        if(userDetailDTO!=null && !userDetailDTO.isEmpty()) {
	        memberDetailResponse.setUserDetailDTO(userDetailDTO);
	        memberDetailResponse.setPageNo(pageUser.getNumber());
	        memberDetailResponse.setPageSize(pageUser.getSize());
	        memberDetailResponse.setTotalElements(pageUser.getTotalElements());
	        memberDetailResponse.setTotalPages(pageUser.getTotalPages());
	        memberDetailResponse.setLast(pageUser.isLast());
        }

        return memberDetailResponse;
		
	}
	
	// convert Entity into DTO
    private UserDetailDTO mapToDTO(User user){
    	UserDetailDTO userDetailDTO =    new UserDetailDTO();
    	UserRegistrationDetailDTO registrationDTO=new UserRegistrationDetailDTO();
    	BeanUtils.copyProperties(user, registrationDTO);
    	userDetailDTO.setUserRegistrationDetailDTO(registrationDTO);
    	if(user.getMemberPersonalDetailEntity()!=null || (user.getMemeberConactList()!=null && !user.getMemeberConactList().isEmpty() ) ) {
    		BeanUtils.copyProperties(user, userDetailDTO);
    	}
    	
    	
    	if(user.getMemberPersonalDetailEntity()!=null) {
    		PersonDetailDTO personDetailDTO=new PersonDetailDTO();
    		BeanUtils.copyProperties(user.getMemberPersonalDetailEntity(), personDetailDTO);
    		userDetailDTO.setPersonDetailDTO(personDetailDTO);
    	}
    	if(user.getMemeberConactList()!=null && !user.getMemeberConactList().isEmpty()) {
    		List<ContactDTO> asDto = user.getMemeberConactList().stream().filter(Objects::nonNull).map(new Function<MemberContactDetailEntity, ContactDTO>() {
    		    @Override
    		    public ContactDTO apply(MemberContactDetailEntity s) {
    		    	ContactDTO contact=null;
    		    	if(s.getActive()==CommonConstants.INT_ONE) {
    		    		contact=new ContactDTO();
    		    		BeanUtils.copyProperties(s, contact);
    		    	}
    		    	return contact;
    		    }
    		}).collect(Collectors.toList());
    		
    		asDto.removeAll(Collections.singleton(null));
    		userDetailDTO.setContactDTO(asDto);

    	}
        return userDetailDTO;
    }


	@Override
	public UserRegistrationDetailDTO getMemberRegistrationDetailsById(ProfileRequest profileRequest) throws Exception {
		User user=this.getUserById(profileRequest.getUserId());
		UserRegistrationDetailDTO userRegistrationDetailDTO=null;
		if(user!=null) {
			userRegistrationDetailDTO=new UserRegistrationDetailDTO();
			BeanUtils.copyProperties(user, userRegistrationDetailDTO);
		}
		return userRegistrationDetailDTO;
	}


	@Override
	public PersonDetailDTO getMemberPersonalDetailsById(ProfileRequest profileRequest) throws Exception {
		PersonDetailDTO personDetailDTO=null;
		Optional<User> userOp = userRepository.findById(profileRequest.getUserId());
		if(userOp.isPresent()) {
			personDetailDTO=new PersonDetailDTO();
			User user=userOp.get();
			MemberPersonalDetailEntity personalDetialEntity=user.getMemberPersonalDetailEntity();
			if(personalDetialEntity!=null) {
				BeanUtils.copyProperties(personalDetialEntity,personDetailDTO);	
			}
			FileItemDTO photoDTO=commonService.getFileByUserIdAndDocTypeId(profileRequest.getUserId(), CommonConstants.INT_ONE);
			if(photoDTO!=null && StringUtils.isNotBlank(photoDTO.getBase64String())) {
				personDetailDTO.setPhoto64(photoDTO.getBase64String());
			}
		}
		return personDetailDTO;
	}


	@Override
	public ContactDetailDTO getMemberContactDetailsById(ProfileRequest profileRequest) throws Exception {
		ContactDetailDTO contactDetailDTO=null;
		Optional<User> userOp = userRepository.findById(profileRequest.getUserId());
		if(userOp.isPresent()) {
			//personDetailDTO=new PersonDetailDTO();
			User user=userOp.get();
			if(user.getMemeberConactList()!=null) {
	    		List<ContactDTO> asDto = user.getMemeberConactList().stream().filter(Objects::nonNull).map(new Function<MemberContactDetailEntity, ContactDTO>() {
	    		    @Override
	    		    public ContactDTO apply(MemberContactDetailEntity s) {
	    		    	ContactDTO contact=null;
	    		    	if(s.getActive()==CommonConstants.INT_ONE) {
	    		    		contact=new ContactDTO();
	    		    		BeanUtils.copyProperties(s, contact);
	    		    	}
	    		    	return contact;
	    		    }
	    		}).collect(Collectors.toList());
	    		
	    		asDto.removeAll(Collections.singleton(null));
	    		contactDetailDTO=new ContactDetailDTO();
	    		contactDetailDTO.setContactDTO(asDto);

	    	}
		}
		return contactDetailDTO;
	}


	@Override
	public CommonResponse saveMemberPersonalDetails(ProfileRequest profileRequest) throws Exception {
		CommonResponse resp=null;
		CustomeUserDetailsServiceUtil.validatePersonalDetialRequest(profileRequest,CommonConstants.SAVE);
		User userDb=null;
		PersonDetailDTO personalDetailsDTO=profileRequest.getPersonDetails();
		Optional<User> userOp = userRepository.findById(profileRequest.getUserId());

		if(userOp.isPresent()) {
			User user=userOp.get();
			personalDetailsDTO.setUserId(profileRequest.getUserId());
			MemberPersonalDetailEntity personalDetialEntity=new MemberPersonalDetailEntity();
			BeanUtils.copyProperties(personalDetailsDTO,personalDetialEntity);
			user.setMemberPersonalDetailEntity(personalDetialEntity);
			personalDetialEntity.setCreatedDate(new Date());
			userDb=userRepository.saveAndFlush(user);
			if(userDb!=null ) {
				resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
			}
		}
		return resp;
	}
	
	@Override
	public CommonResponse saveMemberContactDetails(ProfileRequest profileRequest) throws Exception {
		CommonResponse resp=null;
		CustomeUserDetailsServiceUtil.validatePersonalDetialRequest(profileRequest,CommonConstants.SAVE);
		User userDb=null;
		//PersonDetailDTO personalDetailsDTO=profileRequest.getPersonDetails();
		List<ContactDTO> contactDetailsList=profileRequest.getContactDetails();
		Optional<User> userOp = userRepository.findById(profileRequest.getUserId());
		List<MemberContactDetailEntity>oldContactEntity= null;
		if(userOp.isPresent()) {
			//List<MemberContactDetailEntity> contactEntityList=new ArrayList<>();
			User user=userOp.get();
			oldContactEntity=user.getMemeberConactList();
			if(oldContactEntity!=null) {
				oldContactEntity.forEach(o -> {
					o.setActive(CommonConstants.INT_ZERO);
				});
			}
			//personalDetailsDTO.setUserId(profileRequest.getUserId());
			
			//MemberPersonalDetailEntity personalDetialEntity=new MemberPersonalDetailEntity();
			//BeanUtils.copyProperties(personalDetailsDTO,personalDetialEntity);
			//user.setMemberPersonalDetailEntity(personalDetialEntity);
			
			
			for(ContactDTO contDet:contactDetailsList) {
				MemberContactDetailEntity contEnt=new MemberContactDetailEntity();
				contDet.setUserId(profileRequest.getUserId());
				BeanUtils.copyProperties(contDet, contEnt);
				contEnt.setActive(CommonConstants.INT_ONE);
				contEnt.setUserEntity(user);
				user.addContactDetail(contEnt);
				contEnt.setCreatedDate(new Date());
			}
			
			userDb=userRepository.saveAndFlush(user);
			if(userDb!=null ) {
				List<Long> oldContIds = oldContactEntity.stream().filter(cont->cont.getActive()==0)
                        .map(MemberContactDetailEntity::getContId).collect(Collectors.toList());
				deleteMemberContactDetails(oldContIds);
				resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
				
			}
		}
		return resp;
	}


	@Override
	public void deleteMemberContactDetails(List<Long> oldContIds) throws Exception {
		mcRepository.deleteMemberContactDetailEntityByContIds(oldContIds);
		
	}


	@Override
	public ProfileCompleteStatusDTO getMemberProfileCompletionStatus(Long userId) throws Exception {
		
		ProfileCompleteStatusDTO memProfstatus=new ProfileCompleteStatusDTO();
		Optional<User> userOp = userRepository.findById(userId);
		if(userOp.isPresent()) {
			MemberPersonalDetailEntity personalDetialEntity=userOp.get().getMemberPersonalDetailEntity();
			if(personalDetialEntity!=null) {
				memProfstatus.setPersonalDetail(true);
			}
			List<MemberContactDetailEntity> listContactDetail=userOp.get().getMemeberConactList();
			if(listContactDetail!=null && !listContactDetail.isEmpty()) {
				memProfstatus.setContactDetail(true);
			}
		}
		return memProfstatus;
	}
}
package com.sbmtech.security.services;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.dto.PersonDetailDTO;
import com.sbmtech.dto.UserDetailDTO;
import com.sbmtech.dto.UserRegistrationDetailDTO;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.model.MemberContactDetailEntity;
import com.sbmtech.model.MemberPersonalDetailEntity;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.ProfileRequest;
import com.sbmtech.payload.request.VerifyUserRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.payload.response.MemberDetailResponse;
import com.sbmtech.payload.response.ProfileResponse;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.service.OTPService;
import com.sbmtech.service.impl.CustomeUserDetailsServiceUtil;
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
			user=userRepository.getUserByEmailAndVerified(forgotRequest.getEmail(),true);
			if(!user.isPresent()) {
				ExceptionUtil.throwException(ExceptionValidationsConstants.USERNAME_OR_EMAIL, ExceptionUtil.EXCEPTION_VALIDATION);
			}
		}
		otp=otpService.sendOTP(user.get().getUserId(), user.get().getEmail(),CommonConstants.FLOW_TYPE_FORGETPWD);
		
		
		return otp;
		
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
			//List<MemberContactDetailEntity> contactEntityList=new ArrayList<>();
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


	@Override
	public MemberDetailResponse getAllMemberDetails(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

		List<UserDetailDTO> userDetailDTO=null;
		MemberDetailResponse memberDetailResponse=null;
        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> pageUser = userRepository.findByMemberCategoryAndVerified(CommonConstants.INT_ONE,true ,pageable);

        // get content for page object
        List<User> listOfPosts = pageUser.getContent();
        
        userDetailDTO= listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        userDetailDTO.removeAll(Collections.singleton(null));
        if(userDetailDTO!=null && !userDetailDTO.isEmpty()) {
	        memberDetailResponse = new MemberDetailResponse();
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
    	UserDetailDTO userDetailDTO = null;
    	if(user.getMemberPersonalDetailEntity()!=null || (user.getMemeberConactList()!=null && !user.getMemeberConactList().isEmpty() ) ) {
    		userDetailDTO = new UserDetailDTO();
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
				oldContactEntity.forEach(o -> o.setActive(CommonConstants.INT_ZERO));
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
			}
			
			userDb=userRepository.saveAndFlush(user);
			if(userDb!=null ) {
				resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
			}
		}
		return resp;
	}
}
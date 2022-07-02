package com.sbmtech.security.services;


import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.constant.ExceptionBusinessConstants;
import com.sbmtech.common.constant.ExceptionValidationsConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.common.util.ExcelUploadUtil;
import com.sbmtech.common.util.ValidationUtil;
import com.sbmtech.dto.BankDTO;
import com.sbmtech.dto.BankDetailDTO;
import com.sbmtech.dto.ContactDTO;
import com.sbmtech.dto.ContactDetailDTO;
import com.sbmtech.dto.DocumentDTO;
import com.sbmtech.dto.DocumentDetailDTO;
import com.sbmtech.dto.EduDTO;
import com.sbmtech.dto.EducationDetailDTO;
import com.sbmtech.dto.EmploymentDTO;
import com.sbmtech.dto.EmploymentDetailDTO;
import com.sbmtech.dto.ExcelNewUserDTO;
import com.sbmtech.dto.FileItemDTO;
import com.sbmtech.dto.JobReqWorkPaidDTO;
import com.sbmtech.dto.JobReqWorkTimeDTO;
import com.sbmtech.dto.JobRequestDTO;
import com.sbmtech.dto.JobRequestDetailDTO;
import com.sbmtech.dto.OtpDTO;
import com.sbmtech.dto.PersonDetailDTO;
import com.sbmtech.dto.ProfileCompleteStatusDTO;
import com.sbmtech.dto.UserDetailDTO;
import com.sbmtech.dto.UserRegistrationDetailDTO;
import com.sbmtech.dto.WorkPaidDTO;
import com.sbmtech.dto.WorkTimeDTO;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.model.BankEntity;
import com.sbmtech.model.DocumentEntity;
import com.sbmtech.model.ERole;
import com.sbmtech.model.EducationEntity;
import com.sbmtech.model.EmploymentEntity;
import com.sbmtech.model.GDriveUser;
import com.sbmtech.model.JobReqPaidDetailEntity;
import com.sbmtech.model.JobReqWorkTimeEntity;
import com.sbmtech.model.JobRequestEntity;
import com.sbmtech.model.MemberContactDetailEntity;
import com.sbmtech.model.MemberPersonalDetailEntity;
import com.sbmtech.model.Role;
import com.sbmtech.model.User;
import com.sbmtech.model.WorkPaidDetailEntity;
import com.sbmtech.model.WorkTimeEntity;
import com.sbmtech.payload.request.BankRequest;
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
import com.sbmtech.repository.BankDetailRepository;
import com.sbmtech.repository.DocumentDetailRepository;
import com.sbmtech.repository.EducationDetailRepository;
import com.sbmtech.repository.EmploymentDetailRepository;
import com.sbmtech.repository.GDriveUserRepository;
import com.sbmtech.repository.JobRequestDetailRepository;
import com.sbmtech.repository.MemberContactRepository;
import com.sbmtech.repository.RoleRepository;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.service.CommonService;
import com.sbmtech.service.OTPService;
import com.sbmtech.service.impl.AppSystemPropImpl;
import com.sbmtech.service.impl.AuthServiceUtil;
import com.sbmtech.service.impl.CustomeUserDetailsServiceUtil;
@Service
@Transactional
@DependsOn("AppSystemProp")
public class UserDetailsServiceImpl implements CustomeUserDetailsService {
	
	private static final Logger loggerInfo = Logger.getLogger(CommonConstants.LOGGER_SERVICES_INFO);
	private static final Logger loggerErr = Logger.getLogger(CommonConstants.LOGGER_SERVICES_ERROR);
	
	private static String secretKey;
	
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	GDriveUserRepository gdriveRepository;
	
	@Autowired
	MemberContactRepository mcRepository;
	
	@Autowired
	DocumentDetailRepository docRepository;
	
	@Autowired
	EducationDetailRepository eduRepository;
	
	
	@Autowired
	EmploymentDetailRepository emptRepository;
	
	@Autowired
	JobRequestDetailRepository jobReqRepository;
	
	@Autowired
	BankDetailRepository bankRepository;
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	CommonService commonService;
	
	@PostConstruct
	public void initialize() throws GeneralSecurityException, IOException {
		secretKey = AppSystemPropImpl.props.get("json.secretKey");
	}
	
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
			user=userRepository.getUserByEmailAndVerified(req.getEmail(),false);
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
		Long userId = CommonUtil.getLongValofObject(CommonUtil.decrypt(req.getEncrypedId(), secretKey));
		Optional<User> user=userRepository.findById(userId);
		boolean isRequested=otpService.isUserRequestedForReset(req.getVerificationId(),userId);
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
	
	@Override
	public MemberRegDetailResponse getAllMemberRegDetails(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

		List<UserRegistrationDetailDTO> userRegDetailDTO=null;
		MemberRegDetailResponse memberRegDetailResponse=new MemberRegDetailResponse();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> pageUser = userRepository.findByMemberCategoryAndVerified(CommonConstants.INT_ONE,true ,pageable);

        List<User> listOfPosts = pageUser.getContent();
        
        userRegDetailDTO= listOfPosts.stream().map(post -> mapToUserRegDTO(post)).collect(Collectors.toList());
        if(userRegDetailDTO!=null && !userRegDetailDTO.isEmpty()) {
        	memberRegDetailResponse.setUserRegDetailDTO(userRegDetailDTO);
        	memberRegDetailResponse.setPageNo(pageUser.getNumber());
        	memberRegDetailResponse.setPageSize(pageUser.getSize());
        	memberRegDetailResponse.setTotalElements(pageUser.getTotalElements());
        	memberRegDetailResponse.setTotalPages(pageUser.getTotalPages());
        	memberRegDetailResponse.setLast(pageUser.isLast());
        }

        return memberRegDetailResponse;
		
	}
	
	private UserRegistrationDetailDTO mapToUserRegDTO(User user){
		UserRegistrationDetailDTO userRegDetailDTO =    new UserRegistrationDetailDTO();
    	BeanUtils.copyProperties(user, userRegDetailDTO);
        return userRegDetailDTO;
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
				personDetailDTO.setDob(CommonUtil.getFormattedDate(personalDetialEntity.getDob()));
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
	    		contactDetailDTO.setUserId(profileRequest.getUserId());

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
			personalDetialEntity.setDob(CommonUtil.getDatefromString(personalDetailsDTO.getDob(), CommonConstants.DATE_ddMMyyyy));
			user.setMemberPersonalDetailEntity(personalDetialEntity);
			personalDetialEntity.setCreatedDate(new Date());
			userDb=userRepository.saveAndFlush(user);
			if(userDb!=null ) {
				resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
				resp.setResponseObj(userDb);
			}
		}
		return resp;
	}
	
	@Override
	public CommonResponse saveMemberContactDetails(ProfileRequest profileRequest) throws Exception {
		CommonResponse resp=null;
		CustomeUserDetailsServiceUtil.validatePersonalDetialRequest(profileRequest,CommonConstants.SAVE);
		User userDb=null;
		List<ContactDTO> contactDetailsList=profileRequest.getContactDetails();
		Optional<User> userOp = userRepository.findById(profileRequest.getUserId());
		List<MemberContactDetailEntity>oldContactEntity= null;
		if(userOp.isPresent()) {
			User user=userOp.get();
			oldContactEntity=user.getMemeberConactList();
			if(oldContactEntity!=null && !oldContactEntity.isEmpty()) {
				oldContactEntity.forEach(o -> {
					o.setActive(CommonConstants.INT_ZERO);
				});
			}
			
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
				resp.setResponseObj(userDb);
				
			}
		}
		return resp;
	}


	@Override
	public void deleteMemberContactDetails(List<Long> oldContIds) throws Exception {
		if(oldContIds!=null && !oldContIds.isEmpty()) {
			mcRepository.deleteMemberContactDetailEntityByContIds(oldContIds);
		}
		
	}
	
	@Override
	public void deleteDocumentDetails(List<Long> oldDocIds) throws Exception {
		if(oldDocIds!=null && !oldDocIds.isEmpty()) {
			docRepository.deleteDocumentDetailEntityByDocIds(oldDocIds);
		}
		
	}
	
	@Override
	public void deleteEducationDetails(List<Long> oldEduIds) throws Exception {
		if(oldEduIds!=null && !oldEduIds.isEmpty()) {
			eduRepository.deleteEducationDetailEntityByEduIds(oldEduIds);
		}
		
	}
	@Override
	public void deleteEmploymentDetails(List<Long> oldEmptIds) throws Exception {
		if(oldEmptIds!=null && !oldEmptIds.isEmpty()) {
			emptRepository.deleteEmploymentDetailEntityByEmptIds(oldEmptIds);
		}
		
	}
	
	@Override
	public void deleteJobReqDetails(List<Long> oldJobReqIds) throws Exception {
		if(oldJobReqIds!=null && !oldJobReqIds.isEmpty()) {
			jobReqRepository.deleteJobRequestDetailEntityByJobReqIds(oldJobReqIds);
		}
		
	}
	
	@Override
	public void deleteBankDetails(List<Long> oldCustBankIds) throws Exception {
		if(oldCustBankIds!=null && !oldCustBankIds.isEmpty()) {
			bankRepository.deleteBankDetailEntityByCustBankIds(oldCustBankIds);
		}
		
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
			List<EducationEntity> listEduDetail=userOp.get().getEducationList();
			if(listEduDetail!=null && !listEduDetail.isEmpty()) {
				memProfstatus.setEducationDetail(true);
			}
			List<DocumentEntity> listDocumentDetail=userOp.get().getDocumentList();
			if(listDocumentDetail!=null && !listDocumentDetail.isEmpty()) {
				memProfstatus.setDocumentDetail(true);
			}
			List<EmploymentEntity> listEmploymentDetail=userOp.get().getEmploymentList();
			if(listEmploymentDetail!=null && !listEmploymentDetail.isEmpty()) {
				memProfstatus.setEmploymentDetail(true);
			}
			List<BankEntity> listBankDetail=userOp.get().getBankList();
			if(listBankDetail!=null && !listBankDetail.isEmpty()) {
				memProfstatus.setBankDetail(true);
			}
		}
		return memProfstatus;
	}

	@Override
	public CommonResponse saveDocumentDetails(DocumentRequest docRequeset) throws Exception {
		User userDb=null;
		CommonResponse resp=null;
		DocumentRequest docReq=CustomeUserDetailsServiceUtil.validateSaveDocumentDetailsRequest(docRequeset);
		List<DocumentDTO> docDetailsList=docReq.getDocumentDetails();
		Optional<User> userOp = userRepository.findById(docReq.getUserId());
		List<DocumentEntity>oldDocumentEntity= null;
		if(userOp.isPresent()) {
			User user=userOp.get();
			oldDocumentEntity=user.getDocumentList();
			if(oldDocumentEntity!=null && !oldDocumentEntity.isEmpty()) {
				oldDocumentEntity.forEach(o -> {
					o.setActive(CommonConstants.INT_ZERO);
					try {
						commonService.deleteFile(docReq.getUserId(), o.getDocTypeId());
					} catch (Exception exp) {
						loggerErr.error("GDrive EXCEPTION --> USER_ID : "+docReq.getUserId()+"DocTypeId="+o.getDocTypeId() +", ErrorMSg --> "+exp);
					}
				});
			}
			
			
			if(docDetailsList!=null && !docDetailsList.isEmpty()) {
				for(DocumentDTO docDet:docDetailsList) {
					DocumentEntity docEnt=new DocumentEntity();
					docDet.setUserId(docReq.getUserId());
					BeanUtils.copyProperties(docDet, docEnt);
					docEnt.setActive(CommonConstants.INT_ONE);
					docEnt.setExpiry(CommonUtil.getDatefromString(docDet.getExpiry(), CommonConstants.DATE_ddMMyyyy));
					docEnt.setCreatedDate(new Date());
					docEnt.setUserEntity(user);
					user.addDocumentDetail(docEnt);
					
				}
				
				userDb=userRepository.saveAndFlush(user);
				if(userDb!=null ) {
					List<Long> oldDocIds = oldDocumentEntity.stream().filter(doc->doc.getActive()==0)
	                        .map(DocumentEntity::getDocId).collect(Collectors.toList());
					deleteDocumentDetails(oldDocIds);
					
					resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
					
				}
			}
		}
		return resp;
		
	}

	

	@Override
	public DocumentDetailDTO getDocumentDetailsById(ProfileRequest profileRequest) throws Exception {
		DocumentDetailDTO documentDetailDTO=null;
		Optional<User> userOp = userRepository.findById(profileRequest.getUserId());
		if(userOp.isPresent()) {
			User user=userOp.get();
			if(user.getDocumentList()!=null) {
	    		List<DocumentDTO> asDto = user.getDocumentList().stream().filter(Objects::nonNull).map(new Function<DocumentEntity, DocumentDTO>() {
	    		    @Override
	    		    public DocumentDTO apply(DocumentEntity ent) {
	    		    	DocumentDTO document=null;
	    		    	if(ent.getActive()==CommonConstants.INT_ONE) {
	    		    		document=new DocumentDTO();
	    		    		BeanUtils.copyProperties(ent, document);
	    		    		document.setExpiry(CommonUtil.getFormattedDate(ent.getExpiry()));
	    		    	}
	    		    	return document;
	    		    }
	    		}).collect(Collectors.toList());
	    		
	    		asDto.removeAll(Collections.singleton(null));
	    		documentDetailDTO=new DocumentDetailDTO();
	    		documentDetailDTO.setDocumentDTO(asDto);
	    		documentDetailDTO.setUserId(profileRequest.getUserId());
	    	}
		}
		return documentDetailDTO;

	}

	@Override
	public CommonResponse saveEduDetails(EduRequest eduRequest) throws Exception {
		
		User userDb=null;
		CommonResponse resp=null;
		EduRequest eduReq=CustomeUserDetailsServiceUtil.validateSaveEduRequest(eduRequest);
		List<EduDTO> eduDetailsList=eduReq.getEduDetails();
		Optional<User> userOp = userRepository.findById(eduReq.getUserId());
		List<EducationEntity>oldEduEntity= null;
		if(userOp.isPresent()) {
			User user=userOp.get();
			oldEduEntity=user.getEducationList();
			if(oldEduEntity!=null && !oldEduEntity.isEmpty()) {
				oldEduEntity.forEach(o -> {
					o.setActive(CommonConstants.INT_ZERO);
					try {
						commonService.deleteFile(eduReq.getUserId(), o.getDocTypeId());
					} catch (Exception exp) {
						loggerErr.error("GDrive EXCEPTION --> USER_ID : "+eduReq.getUserId()+" DocTypeId="+o.getDocTypeId() +", ErrorMSg --> "+exp);
					}
				});
			}
			
			
			if(eduDetailsList!=null && !eduDetailsList.isEmpty()) {
				for(EduDTO eduDet:eduDetailsList) {
					EducationEntity eduEnt=new EducationEntity();
					eduDet.setUserId(eduReq.getUserId());
					BeanUtils.copyProperties(eduDet, eduEnt);
					eduEnt.setActive(CommonConstants.INT_ONE);
					eduEnt.setCreatedDate(new Date());
					eduEnt.setUserEntity(user);
					user.addEducationDetail(eduEnt);
					
				}
				
				userDb=userRepository.saveAndFlush(user);
				if(userDb!=null ) {
					List<Long> oldDocIds = oldEduEntity.stream().filter(doc->doc.getActive()==0)
	                        .map(EducationEntity::getEduId).collect(Collectors.toList());
					deleteDocumentDetails(oldDocIds);
					resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
				}
			}
		}
		return resp;
		
	}

	@Override
	public EducationDetailDTO getMemberEduDetailsById(ProfileRequest profileRequest) throws Exception {
		EducationDetailDTO eduDetailDTO=null;
		Optional<User> userOp = userRepository.findById(profileRequest.getUserId());
		if(userOp.isPresent()) {
			User user=userOp.get();
			if(user.getEducationList()!=null) {
	    		List<EduDTO> asDto = user.getEducationList().stream().filter(Objects::nonNull).map(new Function<EducationEntity, EduDTO>() {
	    		    @Override
	    		    public EduDTO apply(EducationEntity ent) {
	    		    	EduDTO edudto=null;
	    		    	if(ent.getActive()==CommonConstants.INT_ONE) {
	    		    		edudto=new EduDTO();
	    		    		BeanUtils.copyProperties(ent, edudto);
	    		    		
	    		    	}
	    		    	return edudto;
	    		    }
	    		}).collect(Collectors.toList());
	    		
	    		asDto.removeAll(Collections.singleton(null));
	    		eduDetailDTO=new EducationDetailDTO();
	    		eduDetailDTO.setEduDTO(asDto);
	    		eduDetailDTO.setUserId(profileRequest.getUserId());
	    	}
		}
		return eduDetailDTO;	
	}

	@Override
	public CommonResponse saveEmploymentDetails(EmploymentRequest employmentRequest) throws Exception {
		User userDb=null;
		CommonResponse resp=null;
		EmploymentRequest empReq=CustomeUserDetailsServiceUtil.validateSaveEmploymentRequest(employmentRequest);
		List<EmploymentDTO> empDetailsList=empReq.getEmploymentDetails();
		Optional<User> userOp = userRepository.findById(empReq.getUserId());
		List<EmploymentEntity>oldEmpEntity= null;
		if(userOp.isPresent()) {
			User user=userOp.get();
			oldEmpEntity=user.getEmploymentList();
			if(oldEmpEntity!=null && !oldEmpEntity.isEmpty()) {
				oldEmpEntity.forEach(o -> {
					o.setActive(CommonConstants.INT_ZERO);
					
				});
			}
			
			
			if(empDetailsList!=null && !empDetailsList.isEmpty()) {
				for(EmploymentDTO empDet:empDetailsList) {
					EmploymentEntity empEnt=new EmploymentEntity();
					empDet.setUserId(empReq.getUserId());
					BeanUtils.copyProperties(empDet, empEnt);
					empEnt.setStartDate(CommonUtil.getDatefromString(empDet.getStartDate(), CommonConstants.DATE_ddMMyyyy));
					empEnt.setEndDate(CommonUtil.getDatefromString(empDet.getEndDate(), CommonConstants.DATE_ddMMyyyy));
					empEnt.setActive(CommonConstants.INT_ONE);
					empEnt.setCreatedDate(new Date());
					empEnt.setUserEntity(user);
					
					List<WorkTimeDTO> workTimeList=empDet.getWorkTimeDetails();
					if(workTimeList!=null && !workTimeList.isEmpty()) {
						for(WorkTimeDTO workTimeDto:workTimeList) {
							WorkTimeEntity workEnt=new WorkTimeEntity();
							WorkPaidDTO paidDTO=workTimeDto.getWorkPaidDetail();
							if(paidDTO!=null) {
								WorkPaidDetailEntity workPaidEnt=new WorkPaidDetailEntity();
								BeanUtils.copyProperties(paidDTO, workPaidEnt);
								workEnt.setWorkPaidDetailEntity(workPaidEnt);
								workPaidEnt.setWorkTimeEntity(workEnt);
							}
							
							
							workEnt.setWorkTimeId(workTimeDto.getWorkTimeId());
							workEnt.setEmploymentEntity(empEnt);
							empEnt.addWorkTimeDetail(workEnt);
						}
					}
					
					user.addEmploymentDetail(empEnt);
					
				}
				
				userDb=userRepository.saveAndFlush(user);
				if(userDb!=null ) {
					List<Long> oldEmptIds = oldEmpEntity.stream().filter(doc->doc.getActive()==0)
	                        .map(EmploymentEntity::getEmptId).collect(Collectors.toList());
					deleteEmploymentDetails(oldEmptIds);
					resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
				}
			}
		}
		return resp;
	}
	
	
	
	@Override
	public EmploymentDetailDTO getMemberEmpDetailsById(ProfileRequest profileRequest) throws Exception {
		EmploymentDetailDTO empDetailDTO=null;
		Optional<User> userOp = userRepository.findById(profileRequest.getUserId());
		
		if(userOp.isPresent()) {
			User user=userOp.get();
			if(user.getEmploymentList()!=null) {
	    		List<EmploymentDTO> asDto = user.getEmploymentList().stream().filter(Objects::nonNull).map(new Function<EmploymentEntity, EmploymentDTO>() {
	    		    @Override
	    		    public EmploymentDTO apply(EmploymentEntity ent) {
	    		    	EmploymentDTO empdto=null;
	    		    	if(ent.getActive()==CommonConstants.INT_ONE) {
	    		    		empdto=new EmploymentDTO();
	    		    		BeanUtils.copyProperties(ent, empdto);
	    		    		empdto.setStartDate(CommonUtil.getFormattedDate(ent.getStartDate()));
	    		    		empdto.setEndDate(CommonUtil.getFormattedDate(ent.getEndDate()));
	    		    		List<WorkTimeEntity> workTimeList=ent.getWorkTimeList();
	    		    		if(workTimeList!=null) {
	    		    			List<WorkTimeDTO> workDtoList = workTimeList.stream().filter(Objects::nonNull).map(new Function<WorkTimeEntity, WorkTimeDTO>() {
	    			    		    @Override
	    			    		    public WorkTimeDTO apply(WorkTimeEntity workTimeEnt) {
	    			    		    	WorkTimeDTO workdto=null;
    			    		    		workdto=new WorkTimeDTO();
    			    		    		BeanUtils.copyProperties(workTimeEnt, workdto);
    			    		    		WorkPaidDetailEntity paidEnt=workTimeEnt.getWorkPaidDetailEntity();
    			    		    		if(paidEnt!=null) {
    			    		    			WorkPaidDTO paidDto=new WorkPaidDTO();
    			    		    			BeanUtils.copyProperties(paidEnt, paidDto);
    			    		    			workdto.setWorkPaidDetail(paidDto);
    			    		    		}
	    			    		    	return workdto;
	    			    		    }
	    			    		}).collect(Collectors.toList());
	    		    			workDtoList.removeAll(Collections.singleton(null));
	    		    			empdto.setWorkTimeDetails(workDtoList);
	    		    		}
	    		    		
	    		    		
	    		    	}
	    		    	return empdto;
	    		    }
	    		}).collect(Collectors.toList());
	    		
	    		asDto.removeAll(Collections.singleton(null));
	    		empDetailDTO=new EmploymentDetailDTO();
	    		empDetailDTO.setEmpDTO(asDto);
	    		empDetailDTO.setUserId(profileRequest.getUserId());
	    	}
		}
		return empDetailDTO;	
	}

	@Override
	public CommonResponse saveJobRequestDetails(JobRequest jobRequest) throws Exception {
		User userDb=null;
		CommonResponse resp=null;
		JobRequest jobReq=CustomeUserDetailsServiceUtil.validateSaveJobRequest(jobRequest);
		List<JobRequestDTO> jobReqDetailsList=jobReq.getJobRequestDetails();
		Optional<User> userOp = userRepository.findById(jobReq.getUserId());
		if(userOp.isPresent()) {
			List<JobRequestEntity>oldJobReqEntity= null;
			User user=userOp.get();
			oldJobReqEntity=user.getJobRequestList();
			if(oldJobReqEntity!=null && !oldJobReqEntity.isEmpty()) {
				oldJobReqEntity.forEach(o -> {
					o.setActive(CommonConstants.INT_ZERO);
					
				});
			}
			
			
			if(jobReqDetailsList!=null && !jobReqDetailsList.isEmpty()) {
				for(JobRequestDTO jobReqDet:jobReqDetailsList) {
					JobRequestEntity jobReqEnt=new JobRequestEntity();
					jobReqDet.setUserId(jobReq.getUserId());
					BeanUtils.copyProperties(jobReqDet, jobReqEnt);
					jobReqEnt.setFreeFrom(CommonUtil.getDatefromString(jobReqDet.getFreeFrom(), CommonConstants.DATE_ddMMyyyy));
					jobReqEnt.setActive(CommonConstants.INT_ONE);
					jobReqEnt.setCreatedDate(new Date());
					jobReqEnt.setUserEntity(user);
					
					List<JobReqWorkTimeDTO> workTimeList=jobReqDet.getJobReqWorkTimeDetails();
					if(workTimeList!=null && !workTimeList.isEmpty()) {
						for(JobReqWorkTimeDTO workTimeDto:workTimeList) {
							JobReqWorkTimeEntity workEnt=new JobReqWorkTimeEntity();
							JobReqWorkPaidDTO paidDTO=workTimeDto.getJobReqWorkPaidDetail();
							if(paidDTO!=null) {
								JobReqPaidDetailEntity workPaidEnt=new JobReqPaidDetailEntity();
								BeanUtils.copyProperties(paidDTO, workPaidEnt);
								workEnt.setJobReqPaidDetailEntity(workPaidEnt);
								workPaidEnt.setJobReqWorkTimeEntity(workEnt);
							}
							
							workEnt.setWorkTimeId(workTimeDto.getWorkTimeId());
							workEnt.setJobRequestEntity(jobReqEnt);
							jobReqEnt.addJobReqWorkTimeDetail(workEnt);
						}
					}
					
					user.addJobRequestDetail(jobReqEnt);
					
				}
				
				userDb=userRepository.saveAndFlush(user);
				if(userDb!=null ) {
					List<Long> oldJobReqIds = oldJobReqEntity.stream().filter(doc->doc.getActive()==0)
	                        .map(JobRequestEntity::getJobReqId).collect(Collectors.toList());
					deleteJobReqDetails(oldJobReqIds);
					resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
				}
			}
		}
		return resp;
		
	}

	@Override
	public JobRequestDetailDTO getMemberJobReqDetailsById(ProfileRequest profileRequest) throws Exception {
		JobRequestDetailDTO jobReqDetailDTO=null;
		Optional<User> userOp = userRepository.findById(profileRequest.getUserId());
		
		if(userOp.isPresent()) {
			User user=userOp.get();
			if(user.getJobRequestList()!=null) {
	    		List<JobRequestDTO> asDto = user.getJobRequestList().stream().filter(Objects::nonNull).map(new Function<JobRequestEntity, JobRequestDTO>() {
	    		    @Override
	    		    public JobRequestDTO apply(JobRequestEntity ent) {
	    		    	JobRequestDTO jobReqdto=null;
	    		    	if(ent.getActive()==CommonConstants.INT_ONE) {
	    		    		jobReqdto=new JobRequestDTO();
	    		    		BeanUtils.copyProperties(ent, jobReqdto);
	    		    		jobReqdto.setFreeFrom(CommonUtil.getFormattedDate(ent.getFreeFrom()));
	    		    		
	    		    		List<JobReqWorkTimeEntity> workTimeList=ent.getJobReqworkTimeList();
	    		    		if(workTimeList!=null) {
	    		    			List<JobReqWorkTimeDTO> workDtoList = workTimeList.stream().filter(Objects::nonNull).map(new Function<JobReqWorkTimeEntity, JobReqWorkTimeDTO>() {
	    			    		    @Override
	    			    		    public JobReqWorkTimeDTO apply(JobReqWorkTimeEntity workTimeEnt) {
	    			    		    	JobReqWorkTimeDTO workdto=null;
    			    		    		workdto=new JobReqWorkTimeDTO();
    			    		    		BeanUtils.copyProperties(workTimeEnt, workdto);
    			    		    		JobReqPaidDetailEntity paidEnt=workTimeEnt.getJobReqPaidDetailEntity();
    			    		    		if(paidEnt!=null) {
    			    		    			JobReqWorkPaidDTO paidDto=new JobReqWorkPaidDTO();
    			    		    			BeanUtils.copyProperties(paidEnt, paidDto);
    			    		    			workdto.setJobReqWorkPaidDetail(paidDto);
    			    		    		}
	    			    		    	return workdto;
	    			    		    }
	    			    		}).collect(Collectors.toList());
	    		    			workDtoList.removeAll(Collections.singleton(null));
	    		    			jobReqdto.setJobReqWorkTimeDetails(workDtoList);
	    		    		}
	    		    		
	    		    		
	    		    	}
	    		    	return jobReqdto;
	    		    }
	    		}).collect(Collectors.toList());
	    		
	    		asDto.removeAll(Collections.singleton(null));
	    		jobReqDetailDTO=new JobRequestDetailDTO();
	    		jobReqDetailDTO.setJobReqDTO(asDto);
	    		jobReqDetailDTO.setUserId(profileRequest.getUserId());
	    	}
		}
		return jobReqDetailDTO;
	}

	public void readExcel(InputStream excelFileToRead) {

		try {
			//excelFileToRead = new FileInputStream("F:\\Projects\\sbm\\requirements\\06-Jun-2022\\02-0-SST-Member Registration.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(excelFileToRead);
			XSSFSheet sheet = wb.getSheetAt(8);
			XSSFRow row;
			XSSFCell celldata;
			Iterator rows = sheet.rowIterator();
			int i=0;
			String email="";
			while (rows.hasNext()) {
				row = (XSSFRow) rows.next();
				if(i!=0 && i!=1) {
					Iterator cells = row.cellIterator();
					ExcelNewUserDTO excelDTO=new ExcelNewUserDTO();
					PersonDetailDTO personDetails=new PersonDetailDTO();
					excelDTO.setPersonDetails(personDetails);
					while (cells.hasNext()) {
						celldata = (XSSFCell) cells.next();
						int emailIndex=celldata.getColumnIndex();
						if(emailIndex==0 && StringUtils.isNotBlank(celldata.toString()) && !ValidationUtil.validateEmail(celldata.toString())) {
							System.out.println("Row Id="+i+" , skipped=Email is wrong");
							excelDTO=null;
							break;
						}else {
							if(emailIndex==0) {
								boolean emailExists=checkEmailIdExistsInDB(celldata.toString());
								if(emailExists) {
									email=celldata.toString();
									break;
								}else {
									excelDTO=ExcelUploadUtil.getCellValue(celldata,excelDTO);
								}
							}else {
								excelDTO=ExcelUploadUtil.getCellValue(celldata,excelDTO);
							}
							
							
						}
						/*
						switch (celldata.getCellType()) {
	
						case STRING:
							System.out.print("str value=" + celldata.getStringCellValue());
							break;
						case NUMERIC:
							System.out.print("num value=" + celldata.getNumericCellValue());
							break;
						case BOOLEAN:
							System.out.print("boo value=" + celldata.getBooleanCellValue());
							break;
						}*/
	
					}
					if(excelDTO!=null && org.apache.commons.lang3.StringUtils.isNotBlank(excelDTO.getEmail())) {
						
						ProfileRequest profileRequest =new ProfileRequest();
						BeanUtils.copyProperties(excelDTO, profileRequest);
						excelDTO.setMemberCategory(CommonConstants.INT_ONE);
						User newUser=saveNewUserFromExcel(excelDTO);
						if(newUser!=null && newUser.getUserId()!=null) {
							System.out.println(">>>>>> User Registered from Excel userDb="+newUser.getUserId()+" ,email="+excelDTO.getEmail());	
						}
						
						profileRequest.setUserId(newUser.getUserId());
						CommonResponse resp=this.saveMemberPersonalDetails(profileRequest);
						profileRequest=ExcelUploadUtil.removeEmptyContactType(profileRequest);
						if(resp!=null && resp.getResponseCode()==CommonConstants.INT_ONE 
								&& resp.getResponseObj()!=null && resp.getResponseObj() instanceof User) {
							User userDb=(User)resp.getResponseObj();
							System.out.println(">>>>>> User personal Detail from Excel userDb="+userDb.getUserId()+" ,email="+excelDTO.getEmail());	
						}
						 resp=this.saveMemberContactDetails(profileRequest);
						if(resp!=null && resp.getResponseCode()==CommonConstants.INT_ONE 
								&& resp.getResponseObj()!=null && resp.getResponseObj() instanceof User) {
							User userDb=(User)resp.getResponseObj();
							System.out.println(">>>>>> User Contact Detail from Excel userDb="+userDb.getUserId()+" ,email="+excelDTO.getEmail());	
						}
					}else if(excelDTO!=null && org.apache.commons.lang3.StringUtils.isBlank(excelDTO.getEmail())) {
						System.out.println(">>>>>> User already in Db for email="+email);
					}
				}
				i++;
				System.out.println("=========================================");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public User saveNewUserFromExcel(ExcelNewUserDTO excelDTO) {
		User user = new User(excelDTO.getEmail(), 
				excelDTO.getFirstname(),
				excelDTO.getLastname(),
				 1,
				 "dummypwd",
				 excelDTO.getEmail());
		Set<String> strRoles = new HashSet<>();
		Set<Role> roles = new HashSet<>();
		if(excelDTO.getMemberCategory()==CommonConstants.INT_ONE_MEMBER) {
			strRoles.add("member");
		}else if(excelDTO.getMemberCategory()==CommonConstants.INT_TWO_GROUP) {
			strRoles.add("group");
		}else if(excelDTO.getMemberCategory()==CommonConstants.INT_THREE_COMPNAY) {
			strRoles.add("company");
		}else {
			strRoles.add("admin");
		}
		
		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				List<Role> adminRole = roleRepository.findAll();
				roles.addAll(adminRole);
				break;
			case "member":
				Role memRole = roleRepository.findByName(ERole.ROLE_MEMBER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(memRole);
				break;
			case "group":
				Role groupRole = roleRepository.findByName(ERole.ROLE_GROUP)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(groupRole);
				break;
			case "company":
				Role companyRole = roleRepository.findByName(ERole.ROLE_COMPANY)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(companyRole);
				break;	
			default:
				Role memDefRole = roleRepository.findByName(ERole.ROLE_MEMBER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(memDefRole);
			}
		});
		
		user.setRoles(roles);
		user.setEnabled(true);
		user.setVerified(false);
		user.setCreatedDate(new Date());
		user.setSource(CommonConstants.SRC_EXCEL);
		User dbUser=userRepository.saveAndFlush(user);
		if(dbUser!=null) {
			try {
				String parentId=commonService.createUserFolder(String.valueOf(dbUser.getUserId()));
				GDriveUser gdriveUser=new GDriveUser();
				gdriveUser.setUserId(dbUser.getUserId());;
				gdriveUser.setParentId(parentId);
				gdriveRepository.save(gdriveUser);
			}catch(Exception ex) {
				loggerErr.error("Exception in GDrive folder creation in excel flow "+ex);
			}
			
		}

		return dbUser;

		
	}

	
	private boolean checkEmailIdExistsInDB(String email) {
		Optional<User> userOp=userRepository.findByEmail(email);
		if(userOp.isPresent()) {
			return true;
		}
		return false;
		/*
		boolean isExists=false;
		List<User> usersList=userRepository.findByEmail(email);
		System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
		if(usersList!=null && !usersList.isEmpty()) {
			isExists=true;
			System.out.println(email +" is there");
		}else {
			System.out.println(email +" is not there");
		}
		return isExists;*/
	}

	@Override
	public CommonResponse saveExcelUpload(MultipartFile file) throws Exception {
		InputStream excelFileToRead=file.getInputStream();
		readExcel(excelFileToRead);		
		return null;
	}

	@Override
	public CommonResponse saveMemberBankDetails(BankRequest bankRequest) throws Exception {
		CommonResponse resp=null;
		User userDb=null;
		BankRequest bankReq=CustomeUserDetailsServiceUtil.validateSaveBankDetailsRequest(bankRequest);
		List<BankDTO> bankDetailsList=bankReq.getBankDetails();
		Optional<User> userOp = userRepository.findById(bankReq.getUserId());
		List<BankEntity>oldBankEntity= null;
		if(userOp.isPresent()) {
			User user=userOp.get();
			oldBankEntity=user.getBankList();
			if(oldBankEntity!=null && !oldBankEntity.isEmpty()) {
				oldBankEntity.forEach(o -> {
					o.setActive(CommonConstants.INT_ZERO);
					
				});
			}
			
			
			if(bankDetailsList!=null && !bankDetailsList.isEmpty()) {
				for(BankDTO bankDet:bankDetailsList) {
					BankEntity bankEnt=new BankEntity();
					bankDet.setUserId(bankReq.getUserId());
					BeanUtils.copyProperties(bankDet, bankEnt);
					bankEnt.setActive(CommonConstants.INT_ONE);
					bankEnt.setCreatedDate(new Date());
					bankEnt.setUserEntity(user);
					user.addBankDetail(bankEnt); 
					
				}
				
				userDb=userRepository.saveAndFlush(user);
				if(userDb!=null ) {
					List<Long> oldBankIds = oldBankEntity.stream().filter(doc->doc.getActive()==0)
	                        .map(BankEntity::getCustBankId).collect(Collectors.toList());
					deleteBankDetails(oldBankIds);
					
					resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
					
				}
			}
		}
		return resp;
	}
	
	
	@Override
	public BankDetailDTO getMemberBankDetailsById(ProfileRequest profileRequest) throws Exception {
		BankDetailDTO bankDetailDTO=null;
		Optional<User> userOp = userRepository.findById(profileRequest.getUserId());
		if(userOp.isPresent()) {
			User user=userOp.get();
			if(user.getBankList()!=null) {
	    		List<BankDTO> asDto = user.getBankList().stream().filter(Objects::nonNull).map(new Function<BankEntity, BankDTO>() {
	    		    @Override
	    		    public BankDTO apply(BankEntity ent) {
	    		    	BankDTO bankdto=null;
	    		    	if(ent.getActive()==CommonConstants.INT_ONE) {
	    		    		bankdto=new BankDTO();
	    		    		BeanUtils.copyProperties(ent, bankdto);
	    		    		
	    		    	}
	    		    	return bankdto;
	    		    }
	    		}).collect(Collectors.toList());
	    		
	    		asDto.removeAll(Collections.singleton(null));
	    		bankDetailDTO=new BankDetailDTO();
	    		bankDetailDTO.setBankDTO(asDto);
	    		bankDetailDTO.setUserId(profileRequest.getUserId());
	    	}
		}
		return bankDetailDTO;	
	}


	
}
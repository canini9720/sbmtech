package com.sbmtech.security.services;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.dto.FileItemDTO;
import com.sbmtech.dto.GroupActivityDTO;
import com.sbmtech.dto.GroupDetailDTO;
import com.sbmtech.dto.GroupInfoDTO;
import com.sbmtech.dto.GroupSubActivityDTO;
import com.sbmtech.dto.NotifEmailDTO;
import com.sbmtech.dto.PartnerDTO;
import com.sbmtech.dto.UserRegistrationDetailDTO;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.model.GroupActivityEntity;
import com.sbmtech.model.GroupDetailsEntity;
import com.sbmtech.model.GroupPartnerDetailEntity;
import com.sbmtech.model.GroupSubActivityEntity;
import com.sbmtech.model.GroupUserActivityEntity;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.GroupActivityRequest;
import com.sbmtech.payload.request.GroupRegRequest;
import com.sbmtech.payload.request.GroupRequest;
import com.sbmtech.payload.request.UserRegRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.payload.response.GroupActivityResponse;
import com.sbmtech.payload.response.GroupRegDetailResponse;
import com.sbmtech.repository.GDriveUserRepository;
import com.sbmtech.repository.GroupActivityMasterRepository;
import com.sbmtech.repository.GroupUserActivityRepository;
import com.sbmtech.repository.RoleRepository;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.service.CommonService;
import com.sbmtech.service.NotificationService;
import com.sbmtech.service.OTPService;
import com.sbmtech.service.impl.AppSystemPropImpl;
import com.sbmtech.service.impl.CustomeUserDetailsServiceUtil;
import com.sbmtech.service.impl.GroupDetailsServiceUtil;
@Service
@Transactional
@DependsOn("AppSystemProp")
public class GroupDetailsServiceImpl implements GroupDetailsService {
	
	private static final Logger loggerInfo = Logger.getLogger(CommonConstants.LOGGER_SERVICES_INFO);
	private static final Logger loggerExcel = Logger.getLogger(CommonConstants.LOGGER_SERVICES_EXCEL_INFO);
	private static final Logger loggerErr = Logger.getLogger(CommonConstants.LOGGER_SERVICES_ERROR);
	
	private static String secretKey;
	
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	GDriveUserRepository gdriveRepository;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	GroupUserActivityRepository groupUserActivityRepo;
	
	@Autowired
	GroupActivityMasterRepository groupActivityMasRepo;
	
	
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	CustomeUserDetailsService userDetailsService;
	
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

	@Override
	public CommonResponse saveGroupDetails(GroupRequest groupRequest) throws Exception {
		CommonResponse resp=null;
		GroupDetailsServiceUtil.validateGroupDetialRequest(groupRequest);
		User userDb=null;
		GroupInfoDTO groupDetailDTO=groupRequest.getGroupDetails();
		Optional<User> userOp = userRepository.findByUserIdAndMemberCategory(groupRequest.getGroupId(),CommonConstants.INT_TWO_GROUP);

		if(userOp.isPresent()) {
			User user=userOp.get();
			groupDetailDTO.setGroupId(groupRequest.getGroupId());
			GroupDetailsEntity groupDetailsEntity=new GroupDetailsEntity();
			BeanUtils.copyProperties(groupDetailDTO,groupDetailsEntity);
			user.setGroupDetailsEntity(groupDetailsEntity);
			groupDetailsEntity.setCreatedDate(new Date());
			List<Long> partnerList=groupRequest.getGroupDetails().getPartnersList();
			for(Long partnerId:partnerList) {
				GroupPartnerDetailEntity partEnt=new GroupPartnerDetailEntity();
				partEnt.setPartnerId(partnerId);
				partEnt.setActive(CommonConstants.INT_ONE);
				partEnt.setGroupDetailsEntity(groupDetailsEntity);
				groupDetailsEntity.addPartnerDetail(partEnt);
				
			}
			userDb=userRepository.saveAndFlush(user);
			if(userDb!=null ) {
				resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
				resp.setResponseObj(userDb);
			}
		}
		return resp;

	}

	@Override
	public GroupDetailDTO getGroupDetailsById(GroupRequest groupRequest) throws Exception {
		GroupDetailDTO groupDetailDTO=null;
		Optional<User> userOp = userRepository.findByUserIdAndMemberCategory(groupRequest.getGroupId(),CommonConstants.INT_TWO_GROUP);
		if(userOp.isPresent()) {
			User user=userOp.get();
			GroupDetailsEntity groupDetailsEntity=user.getGroupDetailsEntity();
			if(groupDetailsEntity!=null) {
				groupDetailDTO=new GroupDetailDTO();
				BeanUtils.copyProperties(groupDetailsEntity, groupDetailDTO);
				Optional<User> managerOp=userRepository.findByUserId(groupDetailsEntity.getGroupMgrId());
				if(managerOp.isPresent()) {
					User mangaerInfo=managerOp.get();
					groupDetailDTO.setGroupMgrName(mangaerInfo.getFirstname()+" "+mangaerInfo.getLastname());
				}
				
	    		List<PartnerDTO> asDto = groupDetailsEntity.getGroupPartnerList().stream().filter(Objects::nonNull).map(new Function<GroupPartnerDetailEntity, PartnerDTO>() {
	    		    @Override
	    		    public PartnerDTO apply(GroupPartnerDetailEntity s) {
	    		    	PartnerDTO partnerDTO=null;
	    		    	if(s.getActive()==CommonConstants.INT_ONE) {
	    		    		partnerDTO=new PartnerDTO();
	    		    		BeanUtils.copyProperties(s, partnerDTO);
	    		    		Optional<User> partnerOp=userRepository.findByUserId(s.getPartnerId());
	    					if(partnerOp.isPresent()) {
	    						User partnerInfo=partnerOp.get();
	    						partnerDTO.setPartnerId(s.getPartnerId());
	    						partnerDTO.setPartnerName(partnerInfo.getFirstname()+" "+partnerInfo.getLastname());
	    						FileItemDTO photoDTO=commonService.getFileByUserIdAndDocTypeId(s.getPartnerId(), CommonConstants.INT_ONE);
	    						if(photoDTO!=null && StringUtils.isNotBlank(photoDTO.getBase64String())) {
	    							partnerDTO.setPhoto64(photoDTO.getBase64String());
	    						}
	    					}
	    		    	}
	    		    	return partnerDTO;
	    		    }
	    		}).collect(Collectors.toList());
	    		
	    		asDto.removeAll(Collections.singleton(null));
	    		groupDetailDTO.setPartnersList(asDto);
	    		groupDetailDTO.setGroupId(groupRequest.getGroupId());

	    	}
		}
		return groupDetailDTO;
	}

	@Override
	public GroupRegDetailResponse getAllGroupRegDetails(int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

		List<UserRegistrationDetailDTO> userRegDetailDTO=null;
		GroupRegDetailResponse groupRegDetailResponse=new GroupRegDetailResponse();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        //Page<User> pageUser = userRepository.findByMemberCategoryAndVerifiedAndNotifyAdminNewuser(CommonConstants.GROUP,true,CommonConstants.INT_ZERO,pageable);
        Page<User> pageUser = userRepository.findByMemberCategoryAndVerified(CommonConstants.GROUP,true,pageable);
        

        List<User> listOfPosts = pageUser.getContent();
        
        userRegDetailDTO= listOfPosts.stream().map(post -> mapToUserRegDTO(post)).collect(Collectors.toList());
        if(userRegDetailDTO!=null && !userRegDetailDTO.isEmpty()) {
        	groupRegDetailResponse.setUserRegDetailDTO(userRegDetailDTO);
        	groupRegDetailResponse.setPageNo(pageUser.getNumber());
        	groupRegDetailResponse.setPageSize(pageUser.getSize());
        	groupRegDetailResponse.setTotalElements(pageUser.getTotalElements());
        	groupRegDetailResponse.setTotalPages(pageUser.getTotalPages());
        	groupRegDetailResponse.setLast(pageUser.isLast());
        }

        return groupRegDetailResponse;
		
	}
	
	private UserRegistrationDetailDTO mapToUserRegDTO(User user){
		UserRegistrationDetailDTO userRegDetailDTO =    new UserRegistrationDetailDTO();
    	BeanUtils.copyProperties(user, userRegDetailDTO);
    	userRegDetailDTO.setRoles(user.getRoles());
        return userRegDetailDTO;
    }

	@Override
	public CommonResponse saveGroupRegistrationDetails(GroupRegRequest groupRegRequest) throws Exception {
		CommonResponse resp = null;
		UserRegRequest req=new UserRegRequest();
		BeanUtils.copyProperties(groupRegRequest, req);
		CustomeUserDetailsServiceUtil.validateUserRegRequest(req);
		User user = userDetailsService.getUserById(CommonUtil.getLongValofObject(req.getUserId()));
		boolean oldEnabled = user.getEnabled();
		if (user != null) {

			user.setFirstname(req.getFirstname());
			user.setLastname(req.getLastname());
			user.setEmail(req.getEmail());
			user.setEnabled(req.isEnabled());
			user.setVerified(req.isVerified());
			user.setNotifyAdminNewuser(CommonConstants.INT_ZERO);
			user.setRoles(groupRegRequest.getRoles());
			resp = new CommonResponse(CommonConstants.SUCCESS_CODE);
			resp.setResponseObj(null);
			if (!oldEnabled && req.isEnabled() && (user.getMemberCategory() == CommonConstants.INT_TWO_GROUP
					|| user.getMemberCategory() == CommonConstants.INT_THREE_COMPNAY)) {
				NotifEmailDTO dto = new NotifEmailDTO();
				dto.setEmailTo(req.getEmail());
				dto.setSubject("Your Account has been Activated.");
				dto.setCustomerName(req.getEmail());

				new Thread(() -> {
					try {
						notificationService.sendAcctActivationEmail(dto);
					} catch (Exception e) {
						loggerErr.error("SERVICE_SEND_OTP_EXCEPTION : " + req.getEmail(), e);
					}
				}).start();

			}

		}
		return resp;
	}

	@Override
	public GroupRegDetailResponse getNewGroupRegDetails(int pageNo, int pageSize, String sortBy, String sortDir) throws Exception {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

		List<UserRegistrationDetailDTO> userRegDetailDTO=null;
		GroupRegDetailResponse groupRegDetailResponse=new GroupRegDetailResponse();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> pageUser = userRepository.findByMemberCategoryAndVerifiedAndNotifyAdminNewuser(CommonConstants.GROUP,true,CommonConstants.INT_ONE,pageable);
        List<User> listOfPosts = pageUser.getContent();
        
        userRegDetailDTO= listOfPosts.stream().map(post -> mapToUserRegDTO(post)).collect(Collectors.toList());
        if(userRegDetailDTO!=null && !userRegDetailDTO.isEmpty()) {
        	groupRegDetailResponse.setUserRegDetailDTO(userRegDetailDTO);
        	groupRegDetailResponse.setPageNo(pageUser.getNumber());
        	groupRegDetailResponse.setPageSize(pageUser.getSize());
        	groupRegDetailResponse.setTotalElements(pageUser.getTotalElements());
        	groupRegDetailResponse.setTotalPages(pageUser.getTotalPages());
        	groupRegDetailResponse.setLast(pageUser.isLast());
        }

        return groupRegDetailResponse;
	}

	@Override
	public CommonResponse saveGroupActivityDetails(GroupActivityRequest req) throws Exception {
		CommonResponse resp=null;
		int result=0;
		ExceptionUtil.throwNullOrEmptyValidationException("UserId", req.getUserId(), true);
		
		Optional<User> userOp = userRepository.findById(req.getUserId());
		List<GroupUserActivityEntity>oldGroupUserActEntity= null;
		if(userOp.isPresent()) {
			User user=userOp.get();
			oldGroupUserActEntity=user.getGroupUserActivityList();
			if(oldGroupUserActEntity!=null && !oldGroupUserActEntity.isEmpty()) {
				oldGroupUserActEntity.forEach(o -> {
					o.setActive(CommonConstants.INT_ZERO);
				});
			}
		}
		List<GroupActivityDTO> activityList=req.getGroupActivityList();
		if(activityList!=null && !activityList.isEmpty()) {
			for(GroupActivityDTO grpActivity: activityList) {
				Integer activityId=grpActivity.getGroupActivityId();
				if(activityId!=0) {
					List<GroupSubActivityDTO> subActivityList=grpActivity.getListGroupSubActivity();
					if(subActivityList!=null && !subActivityList.isEmpty()) {
						for(GroupSubActivityDTO grpSubActivity: subActivityList) {
							if(grpSubActivity!=null && grpSubActivity.getGroupSubActivityId()!=0) {
								GroupUserActivityEntity userSubActivityEnt=new GroupUserActivityEntity();
								userSubActivityEnt.setActivityId(activityId);
								userSubActivityEnt.setUserId(req.getUserId());
								userSubActivityEnt.setSubActivityId(grpSubActivity.getGroupSubActivityId());
								userSubActivityEnt.setActive(CommonConstants.INT_ONE);
								groupUserActivityRepo.saveAndFlush(userSubActivityEnt);
							}
						}
						
					}
				}
			}
			result=1;
			resp = new CommonResponse(CommonConstants.SUCCESS_CODE);
		}
		
		
		if(result==1 && oldGroupUserActEntity!=null && !oldGroupUserActEntity.isEmpty()) {
			List<Long> oldContIds = oldGroupUserActEntity.stream().filter(cont->cont.getActive()==0)
                    .map(GroupUserActivityEntity::getId).collect(Collectors.toList());
			deleteGroupUserActivity(oldContIds);
			resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
		}
		
		return resp;
	}
	
	@Override
	public void deleteGroupUserActivity(List<Long> oldIds) throws Exception {
		if(oldIds!=null && !oldIds.isEmpty()) {
			groupUserActivityRepo.deleteGroupUserActivityIds(oldIds);
		}
		
	}

	@Override
	public GroupActivityResponse getGroupUserActivityDetails(GroupRequest groupRequest) throws Exception {
		
		
		Optional<User> userOp = userRepository.findById(groupRequest.getGroupId());
		List<GroupUserActivityEntity>oldGroupUserActEntity= null;
		List<GroupSubActivityDTO> listGroupSubActivityDTO=null;
		if(userOp.isPresent()) {
			User user=userOp.get();
			oldGroupUserActEntity=user.getGroupUserActivityList();
			if(oldGroupUserActEntity!=null && !oldGroupUserActEntity.isEmpty()) {
				listGroupSubActivityDTO=oldGroupUserActEntity.stream().map(x->{
					GroupSubActivityDTO subActivityDTO=new GroupSubActivityDTO();
					subActivityDTO.setGroupSubActivityId(x.getSubActivityId());
					return subActivityDTO;
				}).collect(Collectors.toList());
			}
		}
		final List<GroupSubActivityDTO> listFinalDTO=listGroupSubActivityDTO;
		
		
		GroupActivityResponse resp=null;
		List<GroupActivityEntity> list=groupActivityMasRepo.findByActive(CommonConstants.INT_ONE);
		List<GroupActivityDTO> grpActDTO = list.stream().
			     map(s -> {
			    	 GroupActivityDTO u = new GroupActivityDTO();
			    u.setGroupActivityId(s.getGrpActivityId());
			    u.setGroupActivity(s.getGrpActivity());
			    
			    List<GroupSubActivityDTO> grpSubActDTO = s.getGroupSubActivityList().stream()
			    		.filter(sa->sa.getActive()==CommonConstants.INT_ONE)
			    		.map(sa->{
				    		GroupSubActivityDTO subAct=new GroupSubActivityDTO();
				    		subAct.setGroupSubActivityId(sa.getGrpSubActivityId());
				    		subAct.setGroupSubActivity(sa.getGrpSubActivity());
				    		for(GroupSubActivityDTO dbDTO:listFinalDTO) {
				    			if(sa.getGrpSubActivityId()==dbDTO.getGroupSubActivityId()) {
				    				subAct.setSelected(CommonConstants.INT_ONE);
				    			}
				    		}
				    		return subAct;
			    	}).collect(Collectors.toList());
			    u.setListGroupSubActivity(grpSubActDTO);
			    return u;
			    }).collect(Collectors.toList());
		
		if(list!=null && !list.isEmpty()) {
			resp=new GroupActivityResponse();
			resp.setGroupActivityList(grpActDTO);
		}
		
		return resp;

	}
	
	
}
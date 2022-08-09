package com.sbmtech.security.services;




import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
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
import com.sbmtech.common.constant.ExceptionValidationsConstants;
import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.dto.BankDetailDTO;
import com.sbmtech.dto.ContactDetailDTO;
import com.sbmtech.dto.DocumentDetailDTO;
import com.sbmtech.dto.FileItemDTO;
import com.sbmtech.dto.GroupActivityDTO;
import com.sbmtech.dto.GroupDetailDTO;
import com.sbmtech.dto.GroupInfoDTO;
import com.sbmtech.dto.GroupSubActivityDTO;
import com.sbmtech.dto.GroupTeamPersonDTO;
import com.sbmtech.dto.NotifEmailDTO;
import com.sbmtech.dto.PartnerDTO;
import com.sbmtech.dto.RoleDTO;
import com.sbmtech.dto.UserRegistrationDetailDTO;
import com.sbmtech.exception.ExceptionUtil;
import com.sbmtech.model.GroupActivityEntity;
import com.sbmtech.model.GroupDetailsEntity;
import com.sbmtech.model.GroupPartnerDetailEntity;
import com.sbmtech.model.GroupTeamContactEntity;
import com.sbmtech.model.GroupTeamPowerEntity;
import com.sbmtech.model.GroupUserActivityEntity;
import com.sbmtech.model.MemberContactDetailEntity;
import com.sbmtech.model.Role;
import com.sbmtech.model.User;
import com.sbmtech.model.UserRoleEntity;
import com.sbmtech.payload.request.BankRequest;
import com.sbmtech.payload.request.DocumentRequest;
import com.sbmtech.payload.request.GroupActivityRequest;
import com.sbmtech.payload.request.GroupRegRequest;
import com.sbmtech.payload.request.GroupRequest;
import com.sbmtech.payload.request.GroupTeamContactRequest;
import com.sbmtech.payload.request.ProfileRequest;
import com.sbmtech.payload.request.UserRegRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.payload.response.GroupActivityResponse;
import com.sbmtech.payload.response.GroupRegDetailResponse;
import com.sbmtech.payload.response.GroupTeamContactResponse;
import com.sbmtech.repository.GDriveUserRepository;
import com.sbmtech.repository.GroupActivityMasterRepository;
import com.sbmtech.repository.GroupTeamContactRepository;
import com.sbmtech.repository.GroupUserActivityRepository;
import com.sbmtech.repository.RoleRepository;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.repository.UserRoleRespositoryCustom;
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
	GroupTeamContactRepository groupTeamContactRepo;
	
	@Autowired
	UserRoleRespositoryCustom userRoleRepo;
	
	
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

	@Override
	public CommonResponse saveGroupContactDetails(GroupRequest groupRequest) throws Exception {
		ProfileRequest pr=new ProfileRequest();
		BeanUtils.copyProperties(groupRequest, pr);
		pr.setUserId(groupRequest.getGroupId());
		pr.setContactDetails(groupRequest.getContactDetails());
		return userDetailsService.saveMemberContactDetails(pr);

	}

	@Override
	public ContactDetailDTO getGroupContactDetails(GroupRequest groupRequest) throws Exception {
		ProfileRequest pr=new ProfileRequest();
		BeanUtils.copyProperties(groupRequest, pr);
		pr.setUserId(groupRequest.getGroupId());
		return userDetailsService.getMemberContactDetailsById(pr);
	}

	@Override
	public CommonResponse saveGroupDocumentDetails(DocumentRequest docRequest) throws Exception {
		return userDetailsService.saveDocumentDetails(docRequest);
	}

	@Override
	public DocumentDetailDTO getGroupDocumentDetails(GroupRequest groupRequest) throws Exception {
		ProfileRequest pr=new ProfileRequest();
		BeanUtils.copyProperties(groupRequest, pr);
		pr.setUserId(groupRequest.getGroupId());
		return userDetailsService.getDocumentDetailsById(pr);

	}

	@Override
	public CommonResponse saveGroupBankDetails(BankRequest bankRequest) throws Exception {
		return userDetailsService.saveMemberBankDetails(bankRequest);
	}

	@Override
	public BankDetailDTO getGroupBankDetailsById(GroupRequest groupRequest) throws Exception {
		ProfileRequest pr=new ProfileRequest();
		BeanUtils.copyProperties(groupRequest, pr);
		pr.setUserId(groupRequest.getGroupId());
		return userDetailsService.getMemberBankDetailsById(pr);
	}

	@Override
	public CommonResponse saveGroupTeamContact(GroupTeamContactRequest groupTeamContactReq) throws Exception {
		loggerInfo.info("saveGroupTeamContact Start by groupid="+groupTeamContactReq.getGroupId());
		CommonResponse resp=null;
		GroupDetailsServiceUtil.validateSaveGroupTeamContactRequest(groupTeamContactReq);
		
		Optional<User> userOp = userRepository.findById(groupTeamContactReq.getGroupId());
		if(userOp.isPresent()) {//groupId
			GroupTeamPersonDTO personDTO=groupTeamContactReq.getGroupTeamPersonDTO();
			Optional<User> memberOp = userRepository.findByUserIdAndMemberCategory(personDTO.getMemberId(),CommonConstants.MEMBER);
			Optional<GroupTeamContactEntity> groupTeamMemberContOp= groupTeamContactRepo.findByGroupIdAndMemberId(groupTeamContactReq.getGroupId(),personDTO.getMemberId());
			if(memberOp.isPresent()) {
				GroupTeamContactEntity grpContactEnt=new GroupTeamContactEntity();
				if(groupTeamMemberContOp.isPresent()) {
					grpContactEnt=groupTeamMemberContOp.get();
				}
				List<RoleDTO> roleList=personDTO.getPowerList();
				List<Role> dbRole=roleRepository.findByForGroupAdmin(CommonConstants.INT_ONE);
				List<Integer> userRoleList = roleList.stream().map(s -> s.getRoleId()).toList();
				List<Integer> dbRoleList = dbRole.stream().map(s->s.getRoleId()).toList();
				loggerInfo.info("user role list="+userRoleList);
				loggerInfo.info("db group role list="+dbRoleList);
				boolean extraRoleAdded=false;
				for(Integer i:userRoleList) {
					extraRoleAdded=!(dbRoleList.contains(i));
					if(extraRoleAdded) {
						loggerInfo.info("user role extra roleId= "+i);
						break;
					}
				}
				if(extraRoleAdded) {
					ExceptionUtil.throwException(ExceptionValidationsConstants.INVALID_ROLE_ID, ExceptionUtil.EXCEPTION_VALIDATION);
				}
				loggerInfo.info("extraRoleAdded="+extraRoleAdded+" ,by groupid="+groupTeamContactReq.getGroupId());
				List<GroupTeamPowerEntity> oldMemPwrList=grpContactEnt.getMemeberPowerList();
				if(oldMemPwrList!=null && !oldMemPwrList.isEmpty()) {
					oldMemPwrList.forEach(o -> {
						o.setActive(CommonConstants.INT_ZERO);
					});
					loggerInfo.info("old pwr list set to inactive="+oldMemPwrList+" ,for memberid="+grpContactEnt.getMemberId());
				}
				grpContactEnt.setGroupId(groupTeamContactReq.getGroupId());
				BeanUtils.copyProperties(groupTeamContactReq.getGroupTeamPersonDTO(), grpContactEnt);
				
				for(RoleDTO roleDTO:roleList) {
					GroupTeamPowerEntity powerEnt=new GroupTeamPowerEntity();
					powerEnt.setRoleId(roleDTO.getRoleId());
					powerEnt.setGroupTeamContactEntity(grpContactEnt);
					powerEnt.setActive(CommonConstants.INT_ONE);
					grpContactEnt.addPowerList(powerEnt);
				}
				loggerInfo.info("New pwr list set to active="+roleList+" ,for memberid="+grpContactEnt.getMemberId());
				grpContactEnt.setActive(CommonConstants.INT_ONE);
				grpContactEnt=groupTeamContactRepo.saveAndFlush(grpContactEnt);
				
				if(grpContactEnt!=null ) {
					
					List<Long> oldGroupTeamPowerIds = oldMemPwrList.stream().filter(cont->cont.getActive()==0)
	                        .map(GroupTeamPowerEntity::getGroupTeamPowerId).collect(Collectors.toList());
					System.out.println("oldGroupTeamPowerIds="+oldGroupTeamPowerIds); //Delete old ids or keep it for record, Right now not deleted
					loggerInfo.info("oldGroupTeamPowerIds="+oldGroupTeamPowerIds+" ,for memberid="+grpContactEnt.getMemberId());
					List<Integer> oldGroupTeamRoleIds=oldMemPwrList.stream().filter(cont->cont.getActive()==0)
							.map(GroupTeamPowerEntity::getRoleId).collect(Collectors.toList());
					System.out.println("oldGroupTeamRoleIds="+oldGroupTeamRoleIds); //Old Role ids 
					if(oldGroupTeamRoleIds!=null && !oldGroupTeamRoleIds.isEmpty()) {
						userRoleRepo.deleteGroupTeamUserRole(grpContactEnt.getMemberId(), oldGroupTeamRoleIds); //Delete old roles from user_roles table
						System.out.println("Old role deleted="+oldGroupTeamRoleIds);
						loggerInfo.info("Old role deleted in user_roles"+oldGroupTeamRoleIds+" ,for memberid="+grpContactEnt.getMemberId());
					}
					//save into user_roles table
					userRoleRepo.saveGroupTeamUserRole(grpContactEnt.getMemberId(), userRoleList);
					resp=new CommonResponse(CommonConstants.SUCCESS_CODE);
					resp.setResponseObj(grpContactEnt);
					
				}
                        
			}else {
				ExceptionUtil.throwException(ExceptionValidationsConstants.INVALID_PERSON_DATA, ExceptionUtil.EXCEPTION_VALIDATION);
			}
			
		}
		loggerInfo.info("saveGroupTeamContact End by groupid="+groupTeamContactReq.getGroupId());
		return resp;
	}

	@Override
	public GroupTeamContactResponse getGroupTeamContact(GroupTeamContactRequest groupTeamContactRequest) {
		GroupTeamContactResponse resp=null;
		List<GroupTeamContactEntity> listGroupTeamEnt=groupTeamContactRepo.findByGroupId(groupTeamContactRequest.getGroupId());
		if(listGroupTeamEnt!=null && !listGroupTeamEnt.isEmpty()) {
			resp=new GroupTeamContactResponse();
			List<GroupTeamPersonDTO> listGroupTeamDto=new ArrayList<GroupTeamPersonDTO>();
			for(GroupTeamContactEntity ent:listGroupTeamEnt) {
				GroupTeamPersonDTO dto=new GroupTeamPersonDTO();
				resp.setGroupId(ent.getGroupId());
				BeanUtils.copyProperties(ent, dto);
				List<GroupTeamPowerEntity> memeberPowerList=ent.getMemeberPowerList();
				if(memeberPowerList!=null && !memeberPowerList.isEmpty()) {
					final List<GroupTeamPowerEntity> memeberPowerListf=memeberPowerList.stream().filter(x->x.getActive()==CommonConstants.INT_ONE).toList();
					List<Role> dbRole=roleRepository.findByForGroupAdmin(CommonConstants.INT_ONE);
					
					List<RoleDTO> listRoleDTO =dbRole.stream().map(x->{
						RoleDTO rolDTO = new RoleDTO();
						for (GroupTeamPowerEntity pow : memeberPowerListf) {
							if (pow.getRoleId() == x.getRoleId()) {
								rolDTO.setRoleId(x.getRoleId());
								rolDTO.setDispName(x.getDispName());
								rolDTO.setSelected(CommonConstants.INT_ONE);
								break;
							}else {
								rolDTO.setRoleId(x.getRoleId());
								rolDTO.setDispName(x.getDispName());
								rolDTO.setSelected(CommonConstants.INT_ZERO);
							}
						}
						return rolDTO;
					}).collect(Collectors.toList());
					//System.out.println("all listRoleDTO "+listRoleDTO);
					dto.setPowerList(listRoleDTO);
				}
				listGroupTeamDto.add(dto);
				
			}
						
			
			resp.setListGroupTeamPersonDTO(listGroupTeamDto);
			
			
			
		}
		return resp;
	}
	
	
}
package com.sbmtech.security.services;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbmtech.common.constant.CommonConstants;
import com.sbmtech.dto.GroupDetailDTO;
import com.sbmtech.model.GroupDetailsEntity;
import com.sbmtech.model.GroupPartnerDetailEntity;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.GroupRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.repository.GDriveUserRepository;
import com.sbmtech.repository.RoleRepository;
import com.sbmtech.repository.UserRepository;
import com.sbmtech.service.CommonService;
import com.sbmtech.service.OTPService;
import com.sbmtech.service.impl.AppSystemPropImpl;
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

	@Override
	public User getUserById(Long userId) throws Exception {
		
		return null;
	}

	@Override
	public CommonResponse saveGroupDetails(GroupRequest groupRequest) throws Exception {
		CommonResponse resp=null;
		GroupDetailsServiceUtil.validateGroupDetialRequest(groupRequest);
		User userDb=null;
		GroupDetailDTO groupDetailDTO=groupRequest.getGroupDetails();
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
				partEnt.setPartnetId(partnerId);
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
	
	
}
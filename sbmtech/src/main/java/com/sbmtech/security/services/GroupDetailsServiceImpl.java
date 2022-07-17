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
import com.sbmtech.payload.request.GroupRequest;
import com.sbmtech.payload.request.JobRequest;
import com.sbmtech.payload.request.ProfileRequest;
import com.sbmtech.payload.request.ResetRequest;
import com.sbmtech.payload.request.UserRegRequest;
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
		
		return null;
	}
	
	
}
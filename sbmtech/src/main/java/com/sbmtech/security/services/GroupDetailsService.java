package com.sbmtech.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sbmtech.dto.GroupDetailDTO;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.GroupRegRequest;
import com.sbmtech.payload.request.GroupRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.payload.response.GroupRegDetailResponse;
import com.sbmtech.payload.response.MemberRegDetailResponse;

public interface GroupDetailsService extends UserDetailsService {


	public CommonResponse saveGroupDetails( GroupRequest groupRequest) throws Exception;
	public GroupDetailDTO getGroupDetailsById(GroupRequest groupRequest) throws Exception ;
	public GroupRegDetailResponse getAllGroupRegDetails(int pageNo, int pageSize, String sortBy, String sortDir)throws Exception;
	public CommonResponse saveGroupRegistrationDetails(GroupRegRequest groupRegRequest)throws Exception;
	public GroupRegDetailResponse getNewGroupRegDetails( int pageNo, int pageSize, String sortBy, String sortDir) throws Exception;
	
	


}

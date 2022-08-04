package com.sbmtech.security.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sbmtech.dto.BankDetailDTO;
import com.sbmtech.dto.ContactDetailDTO;
import com.sbmtech.dto.DocumentDetailDTO;
import com.sbmtech.dto.GroupDetailDTO;
import com.sbmtech.model.User;
import com.sbmtech.payload.request.BankRequest;
import com.sbmtech.payload.request.DocumentRequest;
import com.sbmtech.payload.request.GroupActivityRequest;
import com.sbmtech.payload.request.GroupRegRequest;
import com.sbmtech.payload.request.GroupRequest;
import com.sbmtech.payload.request.ProfileRequest;
import com.sbmtech.payload.response.CommonResponse;
import com.sbmtech.payload.response.GroupActivityResponse;
import com.sbmtech.payload.response.GroupRegDetailResponse;
import com.sbmtech.payload.response.MemberRegDetailResponse;

public interface GroupDetailsService extends UserDetailsService {


	public CommonResponse saveGroupDetails( GroupRequest groupRequest) throws Exception;
	public GroupDetailDTO getGroupDetailsById(GroupRequest groupRequest) throws Exception ;
	public GroupRegDetailResponse getAllGroupRegDetails(int pageNo, int pageSize, String sortBy, String sortDir)throws Exception;
	public CommonResponse saveGroupRegistrationDetails(GroupRegRequest groupRegRequest)throws Exception;
	public GroupRegDetailResponse getNewGroupRegDetails( int pageNo, int pageSize, String sortBy, String sortDir) throws Exception;
	public CommonResponse saveGroupActivityDetails(GroupActivityRequest groupActivityRequest)throws Exception;
	void deleteGroupUserActivity(List<Long> oldIds) throws Exception;
	public GroupActivityResponse getGroupUserActivityDetails(GroupRequest groupRequest)throws Exception;
	public CommonResponse saveGroupContactDetails(GroupRequest groupRequest)throws Exception;
	public ContactDetailDTO getGroupContactDetails(GroupRequest groupRequest)throws Exception;
	public CommonResponse saveGroupDocumentDetails(DocumentRequest docRequest)throws Exception;
	public DocumentDetailDTO getGroupDocumentDetails(GroupRequest groupRequest)throws Exception;
	public CommonResponse saveGroupBankDetails(BankRequest bankRequest)throws Exception;
	public BankDetailDTO getGroupBankDetailsById(GroupRequest groupRequest)throws Exception;
	
	


}

package com.sbmtech.payload.response;

import java.util.List;

import com.sbmtech.common.util.CommonUtil;
import com.sbmtech.dto.UserDetailDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberDetailResponse {
	
	//private Integer responseCode;
	//private String responseMessage;
	//private String responseDesc;
	
	private List<UserDetailDTO> userDetailDTO;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    
    
	public MemberDetailResponse(){
		
	}
	
	
	

}

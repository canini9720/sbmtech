package com.sbmtech.payload.response;

import java.util.List;

import com.sbmtech.dto.UserRegistrationDetailDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberRegDetailResponse {
	
	
	
	private List<UserRegistrationDetailDTO> userRegDetailDTO;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    
    
	public MemberRegDetailResponse(){
		
	}
	
	
	

}

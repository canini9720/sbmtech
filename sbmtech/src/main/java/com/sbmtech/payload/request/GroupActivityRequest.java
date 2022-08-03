package com.sbmtech.payload.request;

import java.util.List;

import com.sbmtech.dto.GroupActivityDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupActivityRequest {
	
	private Long userId;
	private List<GroupActivityDTO> groupActivityList;
	
	
	

}

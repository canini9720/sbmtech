package com.sbmtech.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupResponse extends CommonResponse{

	private String userId;
	
	public SignupResponse(int responseCode) {
		super(responseCode);
	}
	
}

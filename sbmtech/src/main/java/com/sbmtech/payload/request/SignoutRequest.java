package com.sbmtech.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Deprecated 
public class SignoutRequest {
	
	private String sessionId;
	

}

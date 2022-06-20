package com.sbmtech.payload.response;

import java.util.List;

import com.sbmtech.dto.ProfileCompleteStatusDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse {

	private String token;
	private Long userId;
	//private String type;
	private String username;
	//private String email;
	private List<String> roles;
	private String refreshToken;
	private ProfileCompleteStatusDTO memberProfileCompletionStatusDTO;
	
	public JwtResponse(String accessToken, String refreshToken, Long userId, String username, List<String> roles,ProfileCompleteStatusDTO profileCompleteDTO) {
		this.token = accessToken;
		this.refreshToken = refreshToken;
		this.userId = userId;
		this.username = username;
		//this.email = email;
		this.roles = roles;
		this.memberProfileCompletionStatusDTO=profileCompleteDTO;
	}
	
	
}

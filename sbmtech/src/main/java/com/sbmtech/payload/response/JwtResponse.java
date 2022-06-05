package com.sbmtech.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse {

	private String token;
	private Long id;
	private String type;
	private String username;
	//private String email;
	private List<String> roles;
	private String refreshToken;
	public JwtResponse(String accessToken, String refreshToken, Long id, String username, List<String> roles) {
		this.token = accessToken;
		this.refreshToken = refreshToken;
		this.id = id;
		this.username = username;
		//this.email = email;
		this.roles = roles;
	}
	
	
}

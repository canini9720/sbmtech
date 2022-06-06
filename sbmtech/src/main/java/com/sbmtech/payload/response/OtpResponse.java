package com.sbmtech.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OtpResponse extends CommonRespone{

	private String email;
	public OtpResponse(int responseCode) {
		super(responseCode);
	}
}

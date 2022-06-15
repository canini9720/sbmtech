package com.sbmtech.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthErrorResponse extends CommonResponse{

	private String message;
}

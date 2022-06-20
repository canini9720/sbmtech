package com.sbmtech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProfileCompleteStatusDTO {

	private Boolean personalDetail=false;
	private Boolean contactDetail=false;
	private Boolean educationDetail=false;
	private Boolean documentDetail=false;
	private Boolean employmentDetail=false;
}

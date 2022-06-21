package com.sbmtech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PersonDetailDTO {

	private Long userId;
	private String middeName;
	private String dob;
	private String birthPlace;
	private String motherName;
	private String fatherName;
	private String bloodGroup;
	private Integer daughter;
	private Integer son;
	private Long asserts;
	private Long annualIncome;
	private String photo64;
}

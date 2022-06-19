package com.sbmtech.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PersonDetailDTO {

	private Long userId;
	private String middeName;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dob;
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

package com.sbmtech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EduDTO {
	private Long userId;
	private Integer docTypeId;
	private String qualification;
	private String institute;
	private String passedYear;
	private String grade;
	
	private String contentType;
	private String googleFileId;
	private Integer hasAttachment;
	private String base64;
	//private Integer active;
	
	
	
	
}

package com.sbmtech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DocumentDTO {
	private Long userId;
	private Integer docTypeId;
	private String countryAuthority;
	private String docNo;
	private String expiry;
	
	private String contentType;
	private String googleFileId;
	private Integer hasAttachment;
	private Integer active;
	
	private String base64;
}

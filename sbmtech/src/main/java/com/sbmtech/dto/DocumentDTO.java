package com.sbmtech.dto;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	//@JsonFormat(pattern="dd/MM/yyyy")
	private String expiry;
	
	private String contentType;
	private String googleFileId;
	private Integer hasAttachment;
	private Integer active;
//	private Date createdDate;
	
	
	
	
	
}

package com.sbmtech.dto;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String id;

	private String base64String;
	
	private Integer docTypeId;
	
	private String docTypeDesc;

	
	
	

	@Override
	public String toString() {
		return "FileItemDTO [name=" + name +  "]";
	}
	
	

}
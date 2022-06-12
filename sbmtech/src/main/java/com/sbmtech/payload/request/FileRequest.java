package com.sbmtech.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileRequest {
	
	private Long userId;
	private Integer docTypeId;
	

}

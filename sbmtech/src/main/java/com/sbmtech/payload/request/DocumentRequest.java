package com.sbmtech.payload.request;

import java.util.List;

import com.sbmtech.dto.DocumentDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DocumentRequest {
	
	private Long userId;
	private List<DocumentDTO> documentDetails;
	
	

}

package com.sbmtech.payload.request;

import java.util.List;

import com.sbmtech.dto.BankDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BankRequest {
	
	private Long userId;
	private List<BankDTO> bankDetails;
	
	

}

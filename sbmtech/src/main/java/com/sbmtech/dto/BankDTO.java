package com.sbmtech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BankDTO {
	
	private Long userId;
	private Integer bankMasterId;
	private String acctName;
	private String acctNo;
	private String iban;
	private String swift;
	private String bankAddress;
	
	
}

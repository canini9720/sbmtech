package com.sbmtech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ContactDTO {

	
	private Long userId;
	private Integer contactType;
	private String mobileNo;
	private String latitude;
	private String longitude;
	private String flatNo;
	private String shed;
	private String building;
	private String street;
	private String area;
	private String taluk;
	private String district;
	private String state;
	private String country;
	private String pinCode;
	private String poBox;
	private String phone;
	private String fax;
	private String emailSale;
	private String emailShop;
	private String ownerShip;
}

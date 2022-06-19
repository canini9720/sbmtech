package com.sbmtech.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "member_contact_details")
@Setter
@Getter
@NoArgsConstructor
public class MemberContactDetailEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cont_id")
	private Long contId;
	
	
	@Column(name="contact_type")
	private Integer contactType;
		
	@Column(name="mobile_no")
	private String mobileNo;
	
	@Column(name="latitude")
	private String latitude;
	
	@Column(name="longitude")
	private String longitude;
	
	@Column(name="flat_no")
	private String flatNo;
	
	@Column(name="shed")
	private String shed;
	
	@Column(name="building")
	private String building;
	
	@Column(name="street")
	private String street;
	
	@Column(name="area")
	private String area;
	
	@Column(name="taluk")
	private String taluk;
	
	@Column(name="district")
	private String district;
	
	@Column(name="state")
	private String state;
	
	@Column(name="country")
	private String country;
	
	@Column(name="pincode")
	private String pinCode;
	
	@Column(name="pobox")
	private String poBox;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="fax")
	private String fax;
	
	
	@Column(name="email_sale")
	private String emailSale;
	
	@Column(name="email_shop")
	private String emailShop;
	
	@Column(name="ownership")
	private String ownerShip;
	
	@Column(name="active")
	private Integer active;
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userEntity;
	
	
	@Column(name="created_date")
	private Date createdDate;
	
	
	
}
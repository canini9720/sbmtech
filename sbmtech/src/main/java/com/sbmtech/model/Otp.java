package com.sbmtech.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "otp_user")
@Setter
@Getter
@AllArgsConstructor
public class Otp implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="user_id")
	private Long user_id;
	
	@Column(name="otp_code")
	private Integer otpCode;
		
	@Column(name="generated_time")
	private Date generatedTime;
	
	@Column(name="expiry_time")
	private Date expiryTime;
	
	@Column(name="flow_type")
	private String flowType;
	
	@Column(name="email")
	private String email;
	
	
	
}
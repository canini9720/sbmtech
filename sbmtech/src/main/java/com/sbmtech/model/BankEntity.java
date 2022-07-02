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
@Table(name = "bank_details")
@Setter
@Getter
@NoArgsConstructor
public class BankEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cust_bank_id")
	private Long custBankId;

	@Column(name = "bank_mas_id")
	private Integer bankMasterId;
	
	@Column(name = "acct_name")
	private String acctName;
	
	@Column(name = "acct_no")
	private String acctNo;
	
	@Column(name = "iban")
	private String iban;
	
	@Column(name = "swift")
	private String swift;
	

	@Column(name = "bank_address")
	private String bankAddress;
	
	@Column(name = "active")
	private Integer active;
	
	
	
	@Column(name = "created_date")
	private Date createdDate=new Date();
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userEntity;
}

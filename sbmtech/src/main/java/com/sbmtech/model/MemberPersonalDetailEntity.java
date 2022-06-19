package com.sbmtech.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "member_personal_details")
@Setter
@Getter
@NoArgsConstructor
public class MemberPersonalDetailEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID")
	private Long userId;
	
	@Column(name="MIDDLE_NAME")
	private String middeName;
		
	@Column(name="DOB")
	private Date dob;
	
	@Column(name="BIRTH_PLACE")
	private String birthPlace;
	
	@Column(name="MOTHER_NAME")
	private String motherName;
	
	@Column(name="FATHER_NAME")
	private String fatherName;
	
	@Column(name="BLOOD_GROUP")
	private String bloodGroup;
	
	@Column(name="DAUGHTER")
	private Integer daughter;
	
	@Column(name="SON")
	private Integer son;
	
	@Column(name="ASSERT")
	private Long asserts;
	
	@Column(name="ANNUAL_INCOME")
	private Long annualIncome;
	
	@Column(name="created_date")
	private Date createdDate;
	
	
	
	
	
}
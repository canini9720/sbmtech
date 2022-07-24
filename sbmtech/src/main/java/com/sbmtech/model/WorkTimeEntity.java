package com.sbmtech.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employment_work_time_detail")
@Setter
@Getter
@NoArgsConstructor
public class WorkTimeEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empt_work_time_id")
	private Long emptWorkTimeId;

	
	@Column(name = "work_time_id")
	private Integer workTimeId;
		
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="empt_id")
	private EmploymentEntity employmentEntity;
	
	@OneToOne
	@JoinColumn(name="work_time_id",insertable=false, updatable=false)
	WorkTimeMaster workTimeMaster;
	
	
	//@OneToOne(mappedBy = "workTimeEntity", cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
   // private WorkPaidDetailEntity workPaidDetailEntity;
	
	@Column(name = "emp_work_paid_basis_id")
	private Integer paidBasisId;
	

	@Column(name = "amount")
	private Double amount;
	
	
	@Column(name = "currency")
	private String currency;
	
}

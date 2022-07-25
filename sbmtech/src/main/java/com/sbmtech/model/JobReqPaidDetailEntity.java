package com.sbmtech.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
//@Table(name = "job_req_work_paid_detail")
@Setter
@Getter
@NoArgsConstructor
public class JobReqPaidDetailEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "job_req_paid_id")
	private Long jobReqWorkPaidId;

	
	@Column(name = "job_req_paid_basis_id")
	private Integer paidBasisId;
	

	@Column(name = "amount")
	private Double amount;
	
	
	@Column(name = "currency")
	private String currency;
	
	//@OneToOne
	//@JoinColumn(name="paid_basis_id",insertable=false, updatable=false)
	//PaidBasisMaster paidBasisMaster;
	
	
	@OneToOne
    @MapsId
    @JoinColumn(name = "job_req_paid_id")
    private JobReqWorkTimeEntity jobReqWorkTimeEntity;
	
	
	
	
	
	
	
}

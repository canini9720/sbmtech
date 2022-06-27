package com.sbmtech.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "job_request_details")
@Setter
@Getter
@NoArgsConstructor
public class JobRequestEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_req_id")
	private Long jobReqId;

	@Column(name = "designation")
	private String designation;
	
	@Column(name = "job_location")
	private String jobLocation;
	
	@Column(name = "free_from")
	private Date freeFrom;
	
		
	@Column(name = "active")
	private Integer active;
	
	@Column(name = "created_date")
	private Date createdDate=new Date();

	@OneToMany(mappedBy="jobRequestEntity",fetch=FetchType.LAZY,targetEntity=JobReqWorkTimeEntity.class,cascade = CascadeType.ALL)
	private List<JobReqWorkTimeEntity> jobReqworkTimeList= new ArrayList<JobReqWorkTimeEntity>();
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userEntity;
	
	
	public void addJobReqWorkTimeDetail(JobReqWorkTimeEntity jobReqWorkTimeEntity) {
		jobReqworkTimeList.add(jobReqWorkTimeEntity);
		jobReqWorkTimeEntity.setJobRequestEntity(this);
	}
	
}

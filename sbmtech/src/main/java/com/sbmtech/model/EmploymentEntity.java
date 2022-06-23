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
@Table(name = "employment_details")
@Setter
@Getter
@NoArgsConstructor
public class EmploymentEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empt_id")
	private Long emptId;

	@Column(name = "designation")
	private String designation;
	
	@Column(name = "company")
	private String company;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "active")
	private Integer active;
	
	@Column(name = "created_date")
	private Date createdDate=new Date();
	
	@OneToMany(mappedBy="employmentEntity",fetch=FetchType.LAZY,targetEntity=WorkTimeEntity.class,cascade = CascadeType.ALL)
	private List<WorkTimeEntity> workTimeList= new ArrayList<WorkTimeEntity>();
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userEntity;
	
	
	public void addWorkTimeDetail(WorkTimeEntity workTimeEntity) {
		workTimeList.add(workTimeEntity);
		workTimeEntity.setEmploymentEntity(this);
	}
	
}

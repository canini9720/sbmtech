package com.sbmtech.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "work_day_time_master")
@Setter
@Getter
@NoArgsConstructor
public class WorkTimeMaster implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "work_day_id")
	private Integer workDayId;

	@Column(name = "work_desc")
	private String workDesc;
	
	@Column(name = "work_time")
	private String workTime;

	@Column(name = "active")
	private Integer active;
	
}

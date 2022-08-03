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
@Table(name = "grp_sub_activity")
@Setter
@Getter
@NoArgsConstructor
public class GroupSubActivityEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grp_subact_id")
	private Integer grpSubActivityId;
	

	@Column(name = "grp_subact")
	private String grpSubActivity;
	
		
	@Column(name = "active")
	private Integer active;
	
	@ManyToOne
	@JoinColumn(name="grp_act_id")
	private GroupActivityEntity groupActivityEntity;
	/*
	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="grp_subact_id", insertable=false, updatable=false)
	private GroupUserActivityEntity groupUserActivityEntity;*/
	
}

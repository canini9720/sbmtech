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
@Table(name = "grp_user_activity")
@Setter
@Getter
@NoArgsConstructor
public class GroupUserActivityEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	
	@Column(name = "grp_user_id")
	private Long userId;
	

	@Column(name = "grp_act_id")
	private Integer activityId;
	
	
	@Column(name = "grp_subact_id")
	private Integer subActivityId;

	
	@ManyToOne
	@JoinColumn(name="grp_user_id",insertable=false, updatable=false)
	private User userEntity;
	
	
	@Column(name = "active")
	private Integer active;
	/*
	@ManyToOne
	@JoinColumn(name="grp_act_id")
	private GroupActivityEntity groupActivityEntity; */
}

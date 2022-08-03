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
@Table(name = "grp_activity")
@Setter
@Getter
@NoArgsConstructor
public class GroupActivityEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grp_act_id")
	private Integer grpActivityId;

	@Column(name = "grp_act")
	private String grpActivity;
		
	
	@Column(name = "active")
	private Integer active;
	
	
	@OneToMany(mappedBy="groupActivityEntity",fetch=FetchType.LAZY,targetEntity=GroupSubActivityEntity.class,cascade = CascadeType.ALL)
	private List<GroupSubActivityEntity> groupSubActivityList= new ArrayList<GroupSubActivityEntity>();
}

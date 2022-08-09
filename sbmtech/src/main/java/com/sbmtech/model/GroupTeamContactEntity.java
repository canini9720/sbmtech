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
@Table(name = "grp_team_contact")
@Setter
@Getter
@NoArgsConstructor
public class GroupTeamContactEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grp_team_contact_id")
	private Long groupTeamContactId;

	@Column(name = "grp_id")
	private Long groupId;
	
	@Column(name = "member_id")
	private Long memberId;
	
	@Column(name = "designation")
	private String designation;
	
	@Column(name = "status")
	private String status;
	

	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "active")
	private Integer active;
	
	@OneToMany(mappedBy="groupTeamContactEntity",fetch=FetchType.LAZY,targetEntity=GroupTeamPowerEntity.class,cascade = CascadeType.ALL)
	private List<GroupTeamPowerEntity> memeberPowerList= new ArrayList<GroupTeamPowerEntity>();
	
	
	
	public void addPowerList(GroupTeamPowerEntity groupTeamPowerEntity) {
		memeberPowerList.add(groupTeamPowerEntity);
		groupTeamPowerEntity.setGroupTeamContactEntity(this);
	}
}

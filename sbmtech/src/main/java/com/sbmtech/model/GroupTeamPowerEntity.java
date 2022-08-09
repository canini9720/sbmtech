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
@Table(name = "grp_team_power")
@Setter
@Getter
@NoArgsConstructor
public class GroupTeamPowerEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grp_team_pwr_id")
	private Long groupTeamPowerId;

	
	@Column(name = "role_id")
	private Integer roleId;
	
	@Column(name = "active")
	private Integer active;
	
	@ManyToOne
	@JoinColumn(name="grp_team_contact_id")
	private GroupTeamContactEntity groupTeamContactEntity;
}

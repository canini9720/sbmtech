package com.sbmtech.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grp_partner_details")
@Setter
@Getter
@NoArgsConstructor
public class GroupPartnerDetailEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="part_id")
	private Long partId;
	
	
	@Column(name="partner_id")
	private Long partnerId;
		
	
	@Column(name="active")
	private Integer active;
	
	
	@ManyToOne
	@JoinColumn(name="grp_id")
	private GroupDetailsEntity groupDetailsEntity;
	
	
	
	
	
}
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
@Table(name = "contact_type_master")
@Setter
@Getter
@NoArgsConstructor
public class ContactTypeMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cont_type_id")
	private Integer contactTypeId;

	@Column(name = "contact_desc")
	private String contactDesc;
	
	@Column(name = "for_member")
	private Integer forMember;
	
	@Column(name = "for_grp_cmpy")
	private Integer forGroupCompany;
	
	@Column(name = "active")
	private Integer active;
}

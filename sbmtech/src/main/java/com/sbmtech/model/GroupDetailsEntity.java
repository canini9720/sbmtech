package com.sbmtech.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
//@Table(name = "group_details")
@Setter
@Getter
@NoArgsConstructor
public class GroupDetailsEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="group_id")
	private Long userId;
	
	@Column(name="group_name")
	private String groupName;
		
	@Column(name="group_branch")
	private String groupBranch;
		
	@Column(name="group_pobox")
	private String groupPobox;
	
	@Column(name="group_contact_no")
	private String groupContactNo;
	
	@Column(name="created_date")
	private Date createdDate;
	
	
	
	
	
}
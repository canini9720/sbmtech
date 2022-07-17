package com.sbmtech.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grp_details")
@Setter
@Getter
@NoArgsConstructor
public class GroupDetailsEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="grp_id")
	private Long groupId;
	
	@Column(name="grp_name")
	private String groupName;
	
	@Column(name="grp_mgr_id")
	private Long groupMgrId;
		
	@Column(name="grp_branch")
	private String groupBranch;
		
	@Column(name="grp_pobox")
	private String groupPobox;
	
	@Column(name="grp_contact_no")
	private String groupContactNo;

	@Column(name="created_date")
	private Date createdDate;
	
	
	@OneToMany(mappedBy="groupDetailsEntity",fetch=FetchType.LAZY,targetEntity=GroupPartnerDetailEntity.class,cascade = CascadeType.ALL)
	private List<GroupPartnerDetailEntity> groupPartnerList= new ArrayList<GroupPartnerDetailEntity>();
	
	
	public void addPartnerDetail(GroupPartnerDetailEntity partnetEntity) {
		groupPartnerList.add(partnetEntity);
		partnetEntity.setGroupDetailsEntity(this);
	}
	
	
}
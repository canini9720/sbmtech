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
@Table(name = "doc_type_master")
@Setter
@Getter
@NoArgsConstructor
public class DocTypeMaster implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "doc_type_id")
	private Integer docTypeId;

	@Column(name = "file_desc")
	private String fileDesc;
	
	@Column(name = "for_member")
	private Integer forMember;
	
	@Column(name = "for_grp_cmpy")
	private Integer forGroupCompany;
	
	@Column(name = "active")
	private Integer active;
	
	
	@Column(name = "for_mem_doc")
	private Integer forMemDoc;
	
	@Column(name = "for_mem_doc_edu")
	private Integer forMemDocEdu;
	
	
	
}

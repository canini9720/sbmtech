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
@Table(name = "document_details")
@Setter
@Getter
@NoArgsConstructor
public class DocumentEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_id")
	private Long docId;

	@Column(name = "doc_type_id")
	private Integer docTypeId;
	
	@Column(name = "country_authority")
	private String countryAuthority;
	
	@Column(name = "doc_no")
	private String docNo;
	
	@Column(name = "expiry")
	private Date expiry;
	
	@Column(name = "has_attachment")
	private Integer hasAttachment;
	

	@Column(name = "g_drive_file_id")
	private String googleFileId;
	
	
	@Column(name = "content_type")
	private String contentType;
	
	
	@Column(name = "active")
	private Integer active;
	
	
	
	@Column(name = "created_date")
	private Date createdDate=new Date();
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userEntity;
}

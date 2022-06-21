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
@Table(name = "education_details")
@Setter
@Getter
@NoArgsConstructor
public class EducationEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "edu_id")
	private Long eduId;

	@Column(name = "doc_type_id")
	private Integer docTypeId;
	
	@Column(name = "qualification")
	private String qualification;
	
	@Column(name = "grade")
	private String grade;
	
	@Column(name = "institute")
	private String institute;
	
	@Column(name = "passed")
	private String passedYear;
	
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

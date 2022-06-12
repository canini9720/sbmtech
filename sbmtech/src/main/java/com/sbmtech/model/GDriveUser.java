package com.sbmtech.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gdrive_user")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GDriveUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "parent_id")
	private String parentId;
	
	
	@Column(name = "created_date")
	private Date createdDate=new Date();
	
	
}

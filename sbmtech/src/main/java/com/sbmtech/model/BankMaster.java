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
@Table(name = "bank_master")
@Setter
@Getter
@NoArgsConstructor
public class BankMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bank_mas_id")
	private Long bankMasterID;

	@Column(name = "name")
	private String bankName;
	
	@Column(name = "native")
	private Integer nativeBank;
	
	@Column(name = "active")
	private Integer active;
}

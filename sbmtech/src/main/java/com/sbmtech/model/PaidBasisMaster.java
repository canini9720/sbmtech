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
@Table(name = "paid_basis_master")
@Setter
@Getter
@NoArgsConstructor
public class PaidBasisMaster implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "paid_basis_id")
	private Integer paidBasisMasterId;

	@Column(name = "paid_basis_desc")
	private String paidBasisDesc;
	
	

	@Column(name = "active")
	private Integer active;
	
}

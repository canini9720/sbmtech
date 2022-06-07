package com.sbmtech.model;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "system_properties")
@Setter
@Getter
public class SystemProperties {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="name")
	private String name;
	
	@Column(name="value")
	private String value;
	
	
	public SystemProperties() {}
	


}
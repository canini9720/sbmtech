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

@Entity
@Table(name = "user_session")
@Setter
@Getter
@NoArgsConstructor
public class UserSessionEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "session_token")
	private String sessionToken;
	
	@Column(name = "token_expiry_date")
	private Date tokenExpiryDate;
	
	@Column(name = "token_created_date")
	private Date tokenCreatedDate;
}

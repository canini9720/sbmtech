package com.sbmtech.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.UserSessionEntity;


@Repository
public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Long> {


	UserSessionEntity findBySessionToken(String sessionId);
	Integer deleteUserSessionEntityBySessionToken(String sessionId);
	
}
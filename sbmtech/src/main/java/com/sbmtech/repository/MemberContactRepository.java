package com.sbmtech.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.MemberContactDetailEntity;


@Repository
public interface MemberContactRepository extends JpaRepository<MemberContactDetailEntity, Long> {
	
	@Modifying
	@Query("DELETE FROM MemberContactDetailEntity p WHERE p.id IN (:ids)")
	public void deleteMemberContactDetailEntityByContIds(@Param("ids") List<Long> ids);
	
	
}
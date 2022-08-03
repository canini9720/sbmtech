package com.sbmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.GroupUserActivityEntity;



@Repository
public interface GroupUserActivityRepository extends JpaRepository<GroupUserActivityEntity, Integer>{
		
	@Modifying
	@Query("DELETE FROM GroupUserActivityEntity p WHERE p.id IN (:ids)")
	public void deleteGroupUserActivityIds(@Param("ids") List<Long> ids);
}

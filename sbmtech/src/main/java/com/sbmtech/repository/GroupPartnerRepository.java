package com.sbmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.GroupPartnerDetailEntity;
import com.sbmtech.model.GroupUserActivityEntity;



@Repository
public interface GroupPartnerRepository extends JpaRepository<GroupPartnerDetailEntity, Long>{
		
	@Modifying
	@Query("DELETE FROM GroupPartnerDetailEntity p WHERE p.partId IN (:ids)")
	public void deleteOldPartners(@Param("ids") List<Long> ids);
	
	
	
	
	
}

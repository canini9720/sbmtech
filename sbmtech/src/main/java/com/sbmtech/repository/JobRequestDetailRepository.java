package com.sbmtech.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.JobRequestEntity;
@Repository
public interface JobRequestDetailRepository extends JpaRepository<JobRequestEntity, Long> {

	@Modifying
	@Query("DELETE FROM JobRequestEntity p WHERE p.id IN (:ids)")
	public void deleteJobRequestDetailEntityByJobReqIds(@Param("ids") List<Long> ids);
}
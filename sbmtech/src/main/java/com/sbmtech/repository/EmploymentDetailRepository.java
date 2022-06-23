package com.sbmtech.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.EmploymentEntity;
@Repository
public interface EmploymentDetailRepository extends JpaRepository<EmploymentEntity, Long> {

	@Modifying
	@Query("DELETE FROM EmploymentEntity p WHERE p.id IN (:ids)")
	public void deleteEmploymentDetailEntityByEmptIds(@Param("ids") List<Long> ids);
}
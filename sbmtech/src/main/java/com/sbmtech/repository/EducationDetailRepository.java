package com.sbmtech.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.EducationEntity;
@Repository
public interface EducationDetailRepository extends JpaRepository<EducationEntity, Long> {

	@Modifying
	@Query("DELETE FROM EducationEntity p WHERE p.id IN (:ids)")
	public void deleteEducationDetailEntityByEduIds(@Param("ids") List<Long> ids);
}
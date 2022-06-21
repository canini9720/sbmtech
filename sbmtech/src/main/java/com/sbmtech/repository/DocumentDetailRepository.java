package com.sbmtech.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.DocumentEntity;
@Repository
public interface DocumentDetailRepository extends JpaRepository<DocumentEntity, Long> {

	@Modifying
	@Query("DELETE FROM DocumentEntity p WHERE p.id IN (:ids)")
	public void deleteDocumentDetailEntityByDocIds(@Param("ids") List<Long> ids);
}
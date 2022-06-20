package com.sbmtech.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.DocTypeMaster;


@Repository
public interface DocTypeRepository extends JpaRepository<DocTypeMaster, Integer> {
	
	
}
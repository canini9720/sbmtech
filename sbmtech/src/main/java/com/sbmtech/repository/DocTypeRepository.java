package com.sbmtech.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.DocTypeMaster;


@Repository
public interface DocTypeRepository extends JpaRepository<DocTypeMaster, Integer> {
	
	List<DocTypeMaster> findByForGroupCompanyAndActive(Integer forCompany,Integer active);

	List<DocTypeMaster> findByActive(int i);	
}
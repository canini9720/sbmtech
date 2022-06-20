package com.sbmtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.ContactTypeMaster;



@Repository
public interface ContactTypeRepository extends JpaRepository<ContactTypeMaster, Integer>{
		
	
}

package com.sbmtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.BloodGroup;



@Repository
public interface BloodRepository extends JpaRepository<BloodGroup, Long>{
		
	
}

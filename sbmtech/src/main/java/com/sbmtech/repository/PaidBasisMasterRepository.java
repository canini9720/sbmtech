package com.sbmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.DocTypeMaster;
import com.sbmtech.model.PaidBasisMaster;
import com.sbmtech.model.WorkTimeMaster;



@Repository
public interface PaidBasisMasterRepository extends JpaRepository<PaidBasisMaster, Integer>{
		
	List<PaidBasisMaster> findByActive(int i);	
}

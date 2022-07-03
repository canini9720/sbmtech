package com.sbmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.BankMaster;
import com.sbmtech.model.PaidBasisMaster;



@Repository
public interface BankMasterRepository extends JpaRepository<BankMaster, Integer>{
		
	List<BankMaster> findByActive(int i);	
}

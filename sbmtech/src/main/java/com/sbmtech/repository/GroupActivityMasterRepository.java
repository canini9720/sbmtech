package com.sbmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.GroupActivityEntity;



@Repository
public interface GroupActivityMasterRepository extends JpaRepository<GroupActivityEntity, Integer>{
		
	public	List<GroupActivityEntity> findByActive(int i);	
}

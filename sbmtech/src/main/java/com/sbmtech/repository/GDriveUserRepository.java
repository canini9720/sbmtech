package com.sbmtech.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sbmtech.model.GDriveUser;


@Repository
public interface GDriveUserRepository extends JpaRepository<GDriveUser, Long> {

	//GDriveUser findByUserIdAndDocTypeId(Long userId, Integer docTypeId);
	
	
}
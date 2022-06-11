package com.sbmtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.Otp;



@Repository
public interface OTPRepository extends JpaRepository<Otp, Long>, OTPRespositoryCustom{
		
	
}
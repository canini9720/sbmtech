package com.sbmtech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.Otp;
import com.sbmtech.model.User;



@Repository
public interface OTPRepository extends JpaRepository<Otp, Long>, OTPRespositoryCustom{
	Optional<Otp> findByIdAndUserId(Long verificationId,Long userId);
	
}

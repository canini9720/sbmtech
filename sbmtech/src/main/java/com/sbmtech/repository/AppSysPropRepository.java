package com.sbmtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.SystemProperties;

@Repository
public interface AppSysPropRepository extends JpaRepository<SystemProperties, Long> {

}

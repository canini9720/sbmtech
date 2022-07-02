package com.sbmtech.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.BankEntity;
@Repository
public interface BankDetailRepository extends JpaRepository<BankEntity, Long> {

	@Modifying
	@Query("DELETE FROM BankEntity p WHERE p.id IN (:ids)")
	public void deleteBankDetailEntityByCustBankIds(@Param("ids") List<Long> ids);
}
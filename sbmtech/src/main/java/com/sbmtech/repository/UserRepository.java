package com.sbmtech.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findByUserId(Long userId);
	Optional<User> findByEmail(String email);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	Optional<User> findByUserIdAndMemberCategory(Long userId,Integer mc);
	
	Optional<User> getUserByEmailAndVerified(String email,Boolean verified);
	Optional<User> getUserByEmailAndMemberCategory(String email,Integer memberCat);
	Page<User> findByMemberCategory(int intOne, Pageable pageable);
	Page<User> findByMemberCategoryAndVerified(int intOne,boolean  verified, Pageable pageable);
	
	@Query("SELECT U FROM User U WHERE U.memberCategory=1 AND U.enabled=1 AND U.verified=1")
	public List<User> getAllActiveMembers();
	
}
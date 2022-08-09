package com.sbmtech.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbmtech.model.GroupTeamContactEntity;


@Repository
public interface GroupTeamContactRepository extends JpaRepository<GroupTeamContactEntity, Long> {

	List<GroupTeamContactEntity> findByGroupId(Long groupId);
	Optional<GroupTeamContactEntity> findByGroupIdAndMemberId(Long groupId,Long memberId);
}
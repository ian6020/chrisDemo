package com.example.apps.repo.portal;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.apps.domain.portal.Users;

public interface RewardRepository extends CrudRepository<Users, Long> {

	@Query(value = "SELECT TRANS_ID, TOTAL_PURCHASE, TRANS_DATE, CLAIM_FLAG "
			+ " FROM A_TRANSACTION WHERE USERID = ?1 "
			+ " AND TRANS_DATE > DATEADD('DAY', -90, CURRENT_DATE) ", nativeQuery = true)
	List<Object> getRewardPointsForXDays(String userId, int days);
}


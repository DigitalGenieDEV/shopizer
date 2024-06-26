package com.salesmanager.core.business.repositories.ipSafetyCenter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesmanager.core.model.iprsafecenter.IpSafetyCenter;

public interface IpSafetyCenterRepository extends JpaRepository<IpSafetyCenter, Integer> {

	@Modifying
	@Query(value ="UPDATE IPR_SAFETY_CENTER SET "
				+ "REPLY_CONTENT = :#{#ipSafetyCenter.replyContent}, "
				+ "MOD_ID = :#{#ipSafetyCenter.auditSection.modId} , "
				+ "MOD_IP = :#{#ipSafetyCenter.auditSection.modIp}, "
				+ "MOD_DATE = NOW() "
				+ "WHERE ID = :#{#ipSafetyCenter.id} "
				, nativeQuery = true)
	void updateReplyContent(@Param("ipSafetyCenter") IpSafetyCenter ipSafetyCenter);
	
	@Modifying
	@Query(value ="UPDATE IPR_SAFETY_CENTER SET "
				+ "STATE = :#{#ipSafetyCenter.state}, "
				+ "MOD_ID = :#{#ipSafetyCenter.auditSection.modId} , "
				+ "MOD_IP = :#{#ipSafetyCenter.auditSection.modIp}, "
				+ "MOD_DATE = NOW() "
				+ "WHERE ID = :#{#ipSafetyCenter.id} "
				, nativeQuery = true)
	void updateChangeState(@Param("ipSafetyCenter") IpSafetyCenter ipSafetyCenter);
}

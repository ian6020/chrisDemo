package com.example.apps.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apps.bean.AuditEnum;
import com.example.apps.bean.RewardPoint;
import com.example.apps.bean.TransactionList;
import com.example.apps.repo.portal.RewardRepository;
import com.example.apps.service.PointService;
import com.example.apps.service.exception.PortalBaseException;

@Service
public class PointServiceImpl implements PointService {
	
	private static final Logger logger = LoggerFactory.getLogger(PointServiceImpl.class);
	
	@Autowired
	private RewardRepository rewardRepo;
	
	@Override
	public RewardPoint getReward(String userId, int days, int[] max, int[] earn) {
		RewardPoint point = new RewardPoint();
		
		try {
			TransactionList trans = new TransactionList(rewardRepo.getRewardPointsForXDays(userId, days));
			for(int i = 0; i < max.length; i++)
				point.addPoints(trans.getPoints(max[i], earn[i]));
		} catch(PortalBaseException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch(Exception e) {
			logger.error(AuditEnum.InvalidInternalOperation.getMessage(), e);
			throw e;
		}
		
		return point;
	}

}

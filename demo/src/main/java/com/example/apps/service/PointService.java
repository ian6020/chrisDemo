package com.example.apps.service;

import com.example.apps.bean.RewardPoint;

public interface PointService {

	RewardPoint getReward(String userId, int days, int[] max, int[] earn);
}

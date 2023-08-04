package com.example.apps;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.apps.repo.portal.RewardRepository;
import com.example.apps.service.impl.PointServiceImpl;
import com.example.apps.service.PointService;

@SpringBootTest
public class PointServiceMockTest {
	 	@Mock
	    private RewardRepository rewardRepo;

	    @InjectMocks 
	    private PointService pointService = new PointServiceImpl();

	    @BeforeEach
	    void setMockOutput() {
	        when(rewardRepo.getRewardPointsForXDays("demo", 90).size()).thenReturn(5);
	    }

	    @DisplayName("Test Mock helloService + RewardRepository")
	    @Test
	    void testGet() {
	    	int[] x = {100, 50};
	    	int[] y = {2,1};
	        assertTrue("It has reward points", pointService.getReward("demo", 90, x, y).getPoints() > 0);
	    }

}

package com.example.apps;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.apps.service.PointService;

@SpringBootTest
public class PointServiceTest {

	@Autowired
	PointService pointService;

    @DisplayName("Test Spring @Autowired Integration")
    @Test
    void testGet() {
    	int[] x = {100, 50};
    	int[] y = {2,1};
        assertTrue("It has reward points", pointService.getReward("demo", 90, x, y).getPoints() > 0);
    }
}

package com.example.apps.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.apps.service.PointService;

@RestController
@RequestMapping("/reward")
@Validated
public class DemoController {
	
	@Autowired
	PointService pointService;
	
	@GetMapping(path = "/getPoints")
	public ResponseEntity<Object> getHospitalList(@RequestParam String userId,
			@RequestParam int days, @RequestParam int[] maxSpent, 
			@RequestParam int[] earn)
					throws Exception {
				
		return new ResponseEntity<>(pointService.getReward(userId, days, maxSpent, earn), HttpStatus.OK);
	}
	
	@ResponseBody
    @GetMapping("/")
    public String hello() {
        return "Demo Controller";
    }

}

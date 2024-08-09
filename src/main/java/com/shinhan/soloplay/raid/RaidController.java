package com.shinhan.soloplay.raid;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/raid")
@RequiredArgsConstructor
public class RaidController {

	@GetMapping("/attack/{power}")
	int calculateDamage(@PathVariable int power) {
		
		Random random = new Random();

        // 두 개의 주사위를 굴립니다.
        int dice1 = random.nextInt(6) + 1;  // 1부터 6까지의 무작위 숫자
        int dice2 = random.nextInt(6) + 1;  // 1부터 6까지의 무작위 숫자
        
        int multiplier = 1; // 공격 배수
        
        if (dice1 == dice2) {
        	if (dice1 == 6) {
        		multiplier = 3; // 12가 나오면 3배수
        	}else if (dice1 == 1) {
        		multiplier = 0; // 2가 나오면 미스
        	}else {
        		multiplier = 2;
        	}
        }
        
		return power*multiplier; // 최종 대미지
	}
}

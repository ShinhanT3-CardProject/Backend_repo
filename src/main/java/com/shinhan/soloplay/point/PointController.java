package com.shinhan.soloplay.point;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/point")
public class PointController {
	
	@Autowired
	private PointService pointService;
	
	// 사용자 ID에 해당하는 포인트 총합 반환
    @GetMapping("/total/{userId}")
    public ResponseEntity<Integer> getTotalPointsByUserId(@PathVariable String userId) {
        int totalPoints = pointService.getTotalPointsByUserId(userId);
        return new ResponseEntity<>(totalPoints, HttpStatus.OK);
    }

    // 사용자 ID에 해당하는 포인트 리스트 (전체 내역)
    @GetMapping("/{userId}/all")
    public ResponseEntity<List<PointDTO>> getPointsByUserId(@PathVariable String userId) {
        List<PointDTO> points = pointService.getPointsByUserId(userId);
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    // 사용자 ID에 해당하는 포인트 리스트 (적립 내역만)
    @GetMapping("/{userId}/added")
    public ResponseEntity<List<PointDTO>> getAddedPointsByUserId(@PathVariable String userId) {
        List<PointDTO> addedPoints = pointService.getAddedPointsByUserId(userId);
        return new ResponseEntity<>(addedPoints, HttpStatus.OK);
    }

    // 사용자 ID에 해당하는 포인트 리스트 (사용 내역만)
    @GetMapping("/{userId}/used")
    public ResponseEntity<List<PointDTO>> getUsedPointsByUserId(@PathVariable String userId) {
        List<PointDTO> usedPoints = pointService.getUsedPointsByUserId(userId);
        return new ResponseEntity<>(usedPoints, HttpStatus.OK);
    }

    // 새로운 포인트 생성/삭제 및 데이터 베이스 저장
    @PostMapping("/{userId}")
    public ResponseEntity<String> createPoint(@PathVariable String userId, @RequestBody PointDTO pointDTO) {
        try {
        	pointService.createPoint(userId, pointDTO);
        	return ResponseEntity.ok("point issued successfully");
        }catch (RuntimeException e){
        	return ResponseEntity.badRequest().body(e.getMessage());
        }    	
    }
}

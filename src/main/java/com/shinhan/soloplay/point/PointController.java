package com.shinhan.soloplay.point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/point")
public class PointController {
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private HttpSession session;
	
	// 사용자 ID에 해당하는 포인트 총합 반환
    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalPointsByUserId() {
    	String userId = (String) session.getAttribute("loginUser");
    	
    	int totalPoints = pointService.getTotalPointsByUserId(userId);
        return new ResponseEntity<>(totalPoints, HttpStatus.OK);
    }
    
    //카테고리별 포인트 비율을 반환
    @GetMapping("/calc-category")
    public ResponseEntity<Map<String,Object>> getCategoryRatios(){
    	String userId = (String) session.getAttribute("loginUser");
    	
    	if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }    	

    	Map<String, Object> categoryData = pointService.getCategoryData(userId);
        return ResponseEntity.ok(categoryData);
 	
    }
    
    // 사용자 Id, 이름 반환
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        // 세션에서 사용자 ID 및 사용자 이름 가져오기
        String userId = (String) session.getAttribute("loginUser");
        String userName = (String) session.getAttribute("loginUserName");

        if (userId == null || userName == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 결과를 맵으로 포장
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("userName", userName);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    // 사용자 ID에 해당하는 포인트 리스트 (전체 내역)
    @GetMapping("/all")
    public ResponseEntity<List<PointDTO>> getPointsByUserId() {
    	String userId = (String) session.getAttribute("loginUser");
    	if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<PointDTO> points = pointService.getPointsByUserId(userId);
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    // 사용자 ID에 해당하는 포인트 리스트 (적립 내역만)
    @GetMapping("/added")
    public ResponseEntity<List<PointDTO>> getAddedPointsByUserId() {
    	String userId = (String) session.getAttribute("loginUser");
    	if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    	List<PointDTO> addedPoints = pointService.getAddedPointsByUserId(userId);
        return new ResponseEntity<>(addedPoints, HttpStatus.OK);
    }

    // 사용자 ID에 해당하는 포인트 리스트 (사용 내역만)
    @GetMapping("/used")
    public ResponseEntity<List<PointDTO>> getUsedPointsByUserId() {
    	String userId = (String) session.getAttribute("loginUser");
    	if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    	List<PointDTO> usedPoints = pointService.getUsedPointsByUserId(userId);
        return new ResponseEntity<>(usedPoints, HttpStatus.OK);
    }

    // 새로운 포인트 생성/삭제 및 데이터 베이스 저장
    @PostMapping("/create")
    public ResponseEntity<String> createPoint(@RequestBody PointDTO pointDTO) {
    	String userId = (String) session.getAttribute("loginUser");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
    	try {
        	int result = pointService.createPoint(userId, pointDTO);
        	return ResponseEntity.ok(Integer.toString(result));
        }catch (RuntimeException e){
        	return ResponseEntity.badRequest().body(e.getMessage());
        }    	
    }
    
   
}

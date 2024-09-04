package com.shinhan.soloplay.theme;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.point.PointService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/theme")
@RequiredArgsConstructor
public class ThemeController {
	
	final ThemeService themeService;
	final PointService pointService;
	final HttpSession session;
	
	
	// 전체 테마 조회 (공개여부 참) - 완료
	@GetMapping("/findAllTheme/{page}")
	public ResponseEntity<?> findAllTheme(@PathVariable int page) {
		try {
			Page<ThemeDetailResponseDTO> findAllTheme = themeService.findAllTheme(page-1);
			return ResponseEntity.ok(findAllTheme);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
	
	// 카테고리별 테마 조회
	@GetMapping("/findByCategoryId/{categoryId}/{page}")
	public ResponseEntity<?> findByCategory(@PathVariable Long categoryId, @PathVariable int page) {
		try {
			Page<ThemeDetailResponseDTO> themeList = themeService.findByCategory(page-1, categoryId);
			return ResponseEntity.ok(themeList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
		
	// 테마 검색
	@GetMapping("/search/{search}/{page}")
	public ResponseEntity<?> searchByName(@PathVariable String search, @PathVariable int page) {
		try {
			Page<ThemeDetailResponseDTO> themeList = themeService.searchByName(page-1, search);
			return ResponseEntity.ok(themeList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
	
	// 테마 상세 조회, 나의 테마 상세조회 - 완료
	@GetMapping("/findThemeDetail/{themeId}")
	public ResponseEntity<?> findThemeDetail(@PathVariable Long themeId) {
		try {
			ThemeDetailResponseDTO findThemeDetail = themeService.findThemeDetail(themeId);
			return ResponseEntity.ok(findThemeDetail);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("테마를 찾을 수 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
	
	// 나의 테마 조회 - 완료
	@GetMapping("/findMyTheme")
	public ResponseEntity<?> findMyTheme(HttpSession httpSession) {
		String userId = (String) httpSession.getAttribute("loginUser");
		try {
			List<ThemeDetailResponseDTO> findMyTheme = themeService.findMyTheme(userId);
			return ResponseEntity.ok(findMyTheme);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
		
	}
	
	// 테마 수정 (나의 테마 상세조회에서 가능)
	@PutMapping("/updateTheme/{themeId}")
	public ResponseEntity<?> updateTheme(@PathVariable Long themeId,
										@RequestBody ThemeRegisterDTO themeRegisterDTO1,
										HttpSession httpSession) {
		String userId = (String) httpSession.getAttribute("loginUser");
		try {
			ThemeRegisterDTO updateTheme = themeService.updateTheme(themeId, themeRegisterDTO1, userId);
			return ResponseEntity.ok(updateTheme);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정 진행 중 오류가 발생했습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
	
	// 테마 삭제 (나의 테마 상세조회에서 가능) - 완료
	@DeleteMapping("/deleteTheme/{themeId}")
	public ResponseEntity<String> deleteTheme(@PathVariable Long themeId) {
		try {
			themeService.deleteTheme(themeId);
			return ResponseEntity.ok("테마가 성공적으로 삭제되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
	
	// 테마 등록
    @PostMapping("/insertTheme")
    public ResponseEntity<?> insertTheme(@RequestBody ThemeRegisterDTO themeRegisterDTO1, HttpSession httpSession) {
        // 세션에서 로그인된 사용자 ID 가져오기
        String userId = (String) httpSession.getAttribute("loginUser");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        // DTO에 사용자 ID 설정
        themeRegisterDTO1.setUserId(userId);

        // 서비스 계층을 통해 테마 저장
        ThemeRegisterDTO savedThemeDTO = themeService.insertTheme(themeRegisterDTO1);

        // 생성된 테마 정보 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(savedThemeDTO);
    }
    
    // 테마 미션 1개 달성 랜덤포인트
    // 스탬프 미션 달성시 랜덤 포인트 지급
    @PostMapping("/random")
    public ResponseEntity<String> giveRandomPointReward(@RequestBody PointRewardDTO request){
    	String userId = (String) session.getAttribute("loginUser");
    	Long themeContentId = request.getThemeContentId();
    	if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        try {
            int result = pointService.giveRandomPointReward(userId, themeContentId);
            if (result > 0) {
                return ResponseEntity.ok(result + " 포인트가 지급되었습니다.");
            } else {
                return ResponseEntity.badRequest().body("포인트 지급에 실패했습니다.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	
}
package com.shinhan.soloplay.theme;

import java.util.Map;

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

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/theme")
@RequiredArgsConstructor
public class ThemeController {
	
	final ThemeService1 themeService1;
	
	// 전체 테마 조회 (공개여부 참) - 완료
	@GetMapping("/findAllTheme")
	public ResponseEntity<?> findAllTheme() {
		try {
			Map<Long, Map<String, String>> findAllTheme = themeService1.findAllTheme();
			return ResponseEntity.ok(findAllTheme);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
	
	// 테마 상세 조회, 나의 테마 상세조회 - 완료
	@GetMapping("/findThemeDetail/{themeId}")
	public ResponseEntity<?> findThemeDetail(@PathVariable Long themeId) {
		try {
			Map<String ,?>  findThemeDetail = themeService1.findThemeDetail(themeId);
			System.out.println("findThemeDetail : " + findThemeDetail);
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
			Map<Long, Map<String, String>> findMyTheme = themeService1.findMyTheme(userId);
			return ResponseEntity.ok(findMyTheme);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
		
	}
	
	// 테마 수정 (나의 테마 상세조회에서 가능)
	@PutMapping("/updateTheme/{themeId}")
	public ResponseEntity<?> updateTheme(@PathVariable Long themeId,
										@RequestBody ThemeRegisterDTO1 themeRegisterDTO1) {
		try {
			ThemeRegisterDTO1 updateTheme = themeService1.updateTheme(themeId, themeRegisterDTO1);
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
			themeService1.deleteTheme(themeId);
			return ResponseEntity.ok("테마가 성공적으로 삭제되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
	
	// 테마 등록
	@PostMapping("/insertTheme")
	public ResponseEntity<?> insertTheme(@RequestBody ThemeRegisterDTO1 themeRegisterDTO1) {
	    // 여기서 DTO를 서비스로 전달합니다.
	    ThemeRegisterDTO1 insertTheme = themeService1.insertTheme(themeRegisterDTO1);
	    return ResponseEntity.status(HttpStatus.CREATED).body(insertTheme);
	}
	
}
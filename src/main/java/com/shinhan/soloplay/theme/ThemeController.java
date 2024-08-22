package com.shinhan.soloplay.theme;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
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
			System.err.println("findAllTheme 에러체크 : " + findAllTheme.size());
			return ResponseEntity.ok(findAllTheme);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
	
	// 전체 테마 조회 (카테고리별 필터링) 
	@GetMapping("/findAllThemeFilter")
	public ResponseEntity<?> findAllThemeFilter(@RequestParam Long themeMainCategoryId) {
		try {
			List<ThemeSearchDTO1> findAllThemeFilter = themeService1.findAllThemeFilter(themeMainCategoryId);
			return ResponseEntity.ok(findAllThemeFilter);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
	
	// 테마 상세 조회 - 블러오기까지는 완료, 복수의 테마를 담아오려면 조치 필요
	@GetMapping("/findThemeDetail/{themeId}")
	public ResponseEntity<?> findThemeDetail(@PathVariable Long themeId) {
		try {
			Map<String ,?>  findThemeDetail = themeService1.findThemeDetail(themeId);
			return ResponseEntity.ok(findThemeDetail);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("테마를 찾을 수 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
	
	// 나의 테마 조회
	@GetMapping("/findMyTheme")
	public ResponseEntity<?> findMyTheme(HttpSession httpSession) {
		String userId = (String) httpSession.getAttribute("userId");
		try {
			List<ThemeSearchDTO1> findMyTheme = themeService1.findMyTheme(userId);
			return ResponseEntity.ok(findMyTheme);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
		
	}
	
	// 나의 테마 상세조회
	@GetMapping("/findMyThemeDetail/{themeId}")
	public ResponseEntity<?> findMyThemeDetail(@PathVariable Long themeId) {
		try {
			ThemeSearchDTO1 findMyThemeDetail = themeService1.findMyThemeDetail(themeId);
			return ResponseEntity.ok(findMyThemeDetail);
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
	
	// 테마 삭제 (나의 테마 상세조회에서 가능)
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
		try {
			ThemeRegisterDTO1 insertTheme = themeService1.insertTheme(themeRegisterDTO1);
			return ResponseEntity.status(HttpStatus.CREATED).body(insertTheme);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
	
	// 테마 불러오기 (테마 등록, 테마 수정에서 가능)
	@GetMapping("/loadOtherTheme/{themeId}")
	public ResponseEntity<?> loadOtherTheme(@PathVariable Long themeId) {
		try {
			List<ThemeSearchDTO1> loadOtherTheme = themeService1.loadOtherTheme(themeId);
			return ResponseEntity.ok(loadOtherTheme);
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("테마를 불러오는 중 오류가 발생했습니다.");
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}
	
}
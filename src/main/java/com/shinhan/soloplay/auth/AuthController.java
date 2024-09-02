package com.shinhan.soloplay.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.user.UserDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService; 

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    public String getUserIdFromCookie(HttpServletRequest request) {
        String userId = null;

        // 쿠키 배열을 가져옵니다.
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null) {
            // 모든 쿠키를 반복하며, USER_ID 쿠키를 찾습니다.
            for (Cookie cookie : cookies) {
                if ("USER_ID".equals(cookie.getName())) {
                    userId = cookie.getValue();
                    break;
                }
            }
        }

        if (userId != null) {
            return "쿠키에서 가져온 userId: " + userId;
        } else {
            return "쿠키에서 userId를 찾을 수 없습니다.";
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpSession httpSession, HttpServletResponse response) {
        String userId = requestBody.get("userId");
        String userPassword = requestBody.get("userPassword");
        
        Map<String, Object> responseBody = new HashMap<>();
        
        try {
            AuthDTO loginUser = (AuthDTO) authService.loadUserByUsername(userId);
            System.out.println(loginUser);
            if (passwordEncoder.matches(userPassword, loginUser.getUser().getUserPassword())) {
                // 기존 세션을 무효화하고 새로운 세션을 생성하여 세션 고정 공격을 방지합니다.
                
            	httpSession.setAttribute("loginUser", loginUser.getUsername());
            	httpSession.setAttribute("loginUserName", loginUser.getUser().getUserName());
                
                responseBody.put("message", "로그인 성공");
                responseBody.put("user", loginUser.getUser()); // 필요한 사용자 정보 포함
                return ResponseEntity.ok().body(responseBody);
            } else {
                responseBody.put("message", "인증 실패");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
            }

        } catch (UsernameNotFoundException e) {
            responseBody.put("message", "없는 유저입니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            responseBody.put("message", "서버 오류.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }


    
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody UserDTO signUpUser) {
        int signUpResult = authService.signUp(signUpUser);

        if (signUpResult == 1) {
            // 회원가입 성공
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 성공적으로 완료되었습니다.");
        } else if (signUpResult == -1) {
            // 중복된 유저
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 유저입니다.");
        } else {
            // 회원가입 실패
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입에 실패했습니다.");
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        // 세션 무효화
        session.removeAttribute("loginUser");
        session.removeAttribute("loginUserName");
        return ResponseEntity.ok("로그아웃 성공");
    }
}

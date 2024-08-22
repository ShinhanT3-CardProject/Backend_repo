package com.shinhan.soloplay.auth;

import java.net.URLEncoder;
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
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody,HttpServletRequest request, HttpSession httpSession, HttpServletResponse response) {
    	String userId = requestBody.get("userId");
        String userPassword = requestBody.get("userPassword");
        
        System.out.println("HttpSession ----->"+httpSession.getAttribute("loginUser"));
        System.out.println(httpSession.getAttribute("loginUserName"));
        System.out.println();
        try {
            AuthDTO loginUser = (AuthDTO) authService.loadUserByUsername(userId);
            if (passwordEncoder.matches(userPassword, loginUser.getUser().getUserPassword())) {
                httpSession.setAttribute("loginUser", loginUser.getUsername());
                httpSession.setAttribute("loginUserName", loginUser.getUser().getUserName());
//                Cookie cookie = new Cookie("USER_ID", URLEncoder.encode(userId, "UTF-8"));
//                cookie.setHttpOnly(true); // 자바스크립트에서 쿠키 접근 불가하게 설정
//                cookie.setPath("/"); // 쿠키 경로 설정
//                cookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 유효기간 설정 (7일)
//                
//                // SameSite=None과 Secure 설정을 위해
//                cookie.setSecure(true);
//                cookie.setPath("/"); // 필요에 따라 쿠키의 유효 경로 설정
                //response.addCookie(cookie);
                
                return ResponseEntity.ok().body("로그인 성공");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 불일치");
            }

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("없는 유저입니다.");
        } catch (Exception e) {
            // 기타 오류 응답
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류.");
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

}

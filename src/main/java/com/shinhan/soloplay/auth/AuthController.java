package com.shinhan.soloplay.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.user.UserDTO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService; 

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("userId") String userId, 
                        @RequestParam("userPassword") String userPassword, 
                        HttpSession httpSession) {
    	try {
            AuthDTO loginUser = (AuthDTO) authService.loadUserByUsername(userId);

            if (passwordEncoder.matches(userPassword, loginUser.getUser().getUserPassword())) {
                httpSession.setAttribute("loginUser", loginUser);
                return ResponseEntity.ok().body("로그인 성공");
            } else {
            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 불일치");
            }

        } catch (UsernameNotFoundException e) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("없는 유저입니다.");
        }catch (Exception e) {
            // 기타 오류 응답
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류.");
        }
    }
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody UserDTO signUpUser) {
        boolean isSignUpSuccessful = authService.signUp(signUpUser);
        
        if (isSignUpSuccessful) {
            // 회원가입 성공
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 성공적으로 완료되었습니다.");
        } else {
            // 회원가입 실패
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입에 실패했습니다.");
        }
    }
}

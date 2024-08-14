package com.shinhan.soloplay.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class AuthController {

    @Autowired
    AuthService authService; 

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestParam("userId") String userId, 
                        @RequestParam("userPassword") String userPassword, 
                        HttpSession httpSession) {
        try {
            AuthDTO loginUser = (AuthDTO) authService.loadUserByUsername(userId);

            if (passwordEncoder.matches(userPassword, loginUser.getUser().getUserPassword())) {
                httpSession.setAttribute("loginUser", loginUser);
                return "로그인 성공";
            } else {
                return "비밀번호가 일치하지 않습니다.";
            }

        } catch (UsernameNotFoundException e) {
            return "유저를 찾을 수 없습니다.";
        }
    }
}

package com.shinhan.soloplay.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shinhan.soloplay.user.UserDTO;
import com.shinhan.soloplay.user.UserEntity;
import com.shinhan.soloplay.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
	    Optional<UserEntity> userOptional = userRepository.findById(userId);

	    // 사용자 정보를 찾을 수 없는 경우 예외를 던집니다.
	    if (userOptional.isEmpty()) {
	        throw new UsernameNotFoundException("유저를 찾을 수 없습니다.");
	    }

	    UserEntity user = userOptional.get();
	    // AuthDTO를 사용해 사용자 정보를 설정합니다.
	    AuthDTO loginUser = new AuthDTO(userId);
	    loginUser.setUser(UserEntity.toDTO(user));

	    // UserDetails 객체를 반환합니다.
	    return loginUser;
	}
	
	@Transactional
	public int signUp(UserDTO signUpUser) {
	    if (userRepository.findById(signUpUser.getUserId()).isPresent()) {
	        return -1; // 이미 존재하는 유저 (중복)
	    }

	    try {
	        signUpUser.setUserPassword(passwordEncoder.encode(signUpUser.getUserPassword()));
	        UserEntity user = UserEntity.fromDTO(signUpUser);
	        user.setIsActive(true);
	        userRepository.save(user);
	        return 1; // 회원가입 성공
	    } catch (Exception e) {
	        return 0; // 기타 회원가입 실패
	    }
	}
}

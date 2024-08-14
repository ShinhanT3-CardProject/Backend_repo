package com.shinhan.soloplay.auth;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shinhan.soloplay.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO implements UserDetails{
	

    private static final long serialVersionUID = 1L;
    
    private final String ROLE = "ROLE_USER";
    private UserDTO user; 
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//Role User밖에 없기때문에 
		return Collections.singleton(new SimpleGrantedAuthority(ROLE));
	}
	public AuthDTO (String userId,String userPassword) {
		this.user.setUserId(userId);
		this.user.setUserPassword(userPassword);
	}
	
	public AuthDTO (String userId) {
		this.user.setUserId(userId);
	}
	
	@Override
	public String getPassword() {
		return this.user.getUserPassword();
	}
	@Override
	public String getUsername() {
		return this.user.getUserId();
	}

	
}

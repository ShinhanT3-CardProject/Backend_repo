package com.shinhan.soloplay.theme;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shinhan.soloplay.user.UserEntity;

public interface ThemeRepository1 extends JpaRepository<ThemeEntity, Long> {
	
	List<ThemeEntity> findByUser(UserEntity user);
	
}

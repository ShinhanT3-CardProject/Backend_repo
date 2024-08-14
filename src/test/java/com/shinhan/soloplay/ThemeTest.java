package com.shinhan.soloplay;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.soloplay.theme.ThemeRepository1;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ThemeTest {
	
	@Autowired
	ThemeRepository1 themeRepository1;
	
	@Test
	@Transactional
	void findAll() {
		themeRepository1.findAll().forEach(theme ->{ System.out.println(theme);});
	}
	
}

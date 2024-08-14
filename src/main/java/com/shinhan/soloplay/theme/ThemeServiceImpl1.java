package com.shinhan.soloplay.theme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ThemeServiceImpl1 implements ThemeService1 {
	
	@Autowired
	ThemeRepository1 themeRepository1;

	@Override
	public List<ThemeEntity> themeSearchAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}

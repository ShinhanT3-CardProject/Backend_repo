package com.shinhan.soloplay.theme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainCategoryServiceImpl3 implements MainCategoryService3 {

    @Autowired
    private MainCategoryRepository3 mainCategoryRepository;

    @Override
    public List<String> getMainCategoryNameByUserId(String userId) { 
    	// 사용자의 ID로 테마 대분류명을 조회
        return mainCategoryRepository.findMainCategoryNameByUserId(userId);
    }
}

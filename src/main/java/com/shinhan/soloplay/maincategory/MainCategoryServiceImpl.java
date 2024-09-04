package com.shinhan.soloplay.maincategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainCategoryServiceImpl implements MainCategoryService {

    @Autowired
    private MainCategoryRepository mainCategoryRepository;

    @Override
    public List<String> getMainCategoryNameByUserId(String userId) { 
    	// 사용자의 ID로 테마 대분류명을 조회
        return mainCategoryRepository.findMainCategoryNameByUserId(userId);
    }
}

package com.shinhan.soloplay.theme;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThemeServiceImplJK implements ThemeServiceJK {

    final MainCategoryRepositoryJK mainCategoryRepo;

    public List<MainCategoryEntity> getAllMainCategories() {
        return mainCategoryRepo.findAll();
    }

    public List<SubCategoryEntity> getSubCategoriesByMainCategory(Long mainCategoryId) {
        return mainCategoryRepo.findById(mainCategoryId)
            .map(MainCategoryEntity::getSubCategories)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Main Category ID: " + mainCategoryId));
    }
}

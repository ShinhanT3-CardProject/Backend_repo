package com.shinhan.soloplay.theme;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThemeServiceJK {

    private final ThemeRepositoryJK themeRepositoryJK;

    @Autowired
    public ThemeServiceJK(ThemeRepositoryJK themeRepositoryJK) {
        this.themeRepositoryJK = themeRepositoryJK;
    }

    public List<MainCategoryEntity> getAllMainCategories() {
        return themeRepositoryJK.findAll();
    }

    public List<SubCategoryEntity> getSubCategoriesByMainCategory(Long mainCategoryId) {
        return themeRepositoryJK.findById(mainCategoryId)
            .map(MainCategoryEntity::getSubCategories)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Main Category ID: " + mainCategoryId));
    }
}

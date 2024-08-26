package com.shinhan.soloplay.theme;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class MainCategoryController {

    final ThemeServiceJK theneServiceJK;

    @GetMapping
    public List<MainCategoryDTO> getAllMainCategories() {
        return theneServiceJK.getAllMainCategories().stream()
                .map(entity -> new MainCategoryDTO(entity.getThemeMainCategoryId(), entity.getThemeMainCategoryName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{mainCategoryId}/subcategories")
    public List<SubCategoryDTO> getSubCategoriesByMainCategory(@PathVariable Long mainCategoryId) {
        return theneServiceJK.getSubCategoriesByMainCategory(mainCategoryId).stream()
                .map(entity -> new SubCategoryDTO(entity.getThemeSubCategoryId(), entity.getThemeSubCategoryName(), entity.getMainCategory().getThemeMainCategoryName()))
                .collect(Collectors.toList());
    }
}
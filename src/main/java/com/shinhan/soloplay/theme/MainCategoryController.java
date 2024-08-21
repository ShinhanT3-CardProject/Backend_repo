package com.shinhan.soloplay.theme;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class MainCategoryController {

    private final ThemeServiceJK mainCategoryService;

    @Autowired
    public MainCategoryController(ThemeServiceJK mainCategoryService) {
        this.mainCategoryService = mainCategoryService;
    }

    @GetMapping
    public List<MainCategoryDTO> getAllMainCategories() {
        return mainCategoryService.getAllMainCategories().stream()
                .map(entity -> new MainCategoryDTO(entity.getThemeMainCategoryId(), entity.getThemeMainCategoryName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{mainCategoryId}/subcategories")
    public List<SubCategoryDTO> getSubCategoriesByMainCategory(@PathVariable Long mainCategoryId) {
        return mainCategoryService.getSubCategoriesByMainCategory(mainCategoryId).stream()
                .map(entity -> new SubCategoryDTO(entity.getThemeSubCategoryId(), entity.getThemeSubCategoryName()))
                .collect(Collectors.toList());
    }
}

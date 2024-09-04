package com.shinhan.soloplay.theme;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.maincategory.MainCategoryDTO;
import com.shinhan.soloplay.subcategory.SubCategoryDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class ThemeControllerJK {

    final ThemeService themeService;

    @GetMapping
    public List<MainCategoryDTO> getAllMainCategories() {
        return themeService.getAllMainCategories().stream()
                .map(entity -> new MainCategoryDTO(entity.getThemeMainCategoryId(), entity.getThemeMainCategoryName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{mainCategoryId}/subcategories")
    public List<SubCategoryDTO> getSubCategoriesByMainCategory(@PathVariable Long mainCategoryId) {
        return themeService.getSubCategoriesByMainCategory(mainCategoryId).stream()
                .map(entity -> new SubCategoryDTO(entity.getThemeSubCategoryId(), entity.getThemeSubCategoryName(), entity.getMainCategory().getThemeMainCategoryName()))
                .collect(Collectors.toList());
    }
    
    
    @GetMapping("/themeCount")
    public Long getTheme(HttpSession httpSession) {
    	String userId = (String) httpSession.getAttribute("loginUser");
    	return themeService.getThemeCount(userId);
    }
}
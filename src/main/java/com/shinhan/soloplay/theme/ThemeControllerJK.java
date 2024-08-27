package com.shinhan.soloplay.theme;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class ThemeControllerJK {

    final ThemeServiceJK themeServiceJK;

    @GetMapping
    public List<MainCategoryDTO> getAllMainCategories() {
        return themeServiceJK.getAllMainCategories().stream()
                .map(entity -> new MainCategoryDTO(entity.getThemeMainCategoryId(), entity.getThemeMainCategoryName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{mainCategoryId}/subcategories")
    public List<SubCategoryDTO> getSubCategoriesByMainCategory(@PathVariable Long mainCategoryId) {
        return themeServiceJK.getSubCategoriesByMainCategory(mainCategoryId).stream()
                .map(entity -> new SubCategoryDTO(entity.getThemeSubCategoryId(), entity.getThemeSubCategoryName(), entity.getMainCategory().getThemeMainCategoryName()))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/isActivated")
    public Long getIsActivated(HttpSession httpSession) {
    	String userId = (String) httpSession.getAttribute("loginUser");
    	return themeServiceJK.getIsActivated(userId);
    }
    
    @GetMapping("/themeCount")
    public Long getTheme(HttpSession httpSession) {
    	String userId = (String) httpSession.getAttribute("loginUser");
    	return themeServiceJK.getThemeCount(userId);
    }
}
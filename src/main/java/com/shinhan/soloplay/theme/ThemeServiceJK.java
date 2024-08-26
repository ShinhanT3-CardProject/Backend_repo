package com.shinhan.soloplay.theme;

import java.util.List;

public interface ThemeServiceJK {

	public List<MainCategoryEntity> getAllMainCategories();

    public List<SubCategoryEntity> getSubCategoriesByMainCategory(Long mainCategoryId);
}

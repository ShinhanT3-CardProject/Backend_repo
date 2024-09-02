package com.shinhan.soloplay.theme;

import java.util.List;

public interface ThemeServiceJK {

	public List<MainCategoryEntity> getAllMainCategories();

    public List<SubCategoryEntity> getSubCategoriesByMainCategory(Long mainCategoryId);

    public int getIsSuccess(String userId);
    
    public void getIsActivated(String userId);
    
    public Long getThemeCount(String userId);
}

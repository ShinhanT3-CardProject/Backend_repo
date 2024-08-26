package com.shinhan.soloplay.theme;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.soloplay.user.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThemeServiceImplJK implements ThemeServiceJK {

    final MainCategoryRepositoryJK mainCategoryRepo;
    final ThemeRepository1 themeRepository1;
    final ThemeContentRepository themeContentRepository;

    public List<MainCategoryEntity> getAllMainCategories() {
        return mainCategoryRepo.findAll();
    }

    public List<SubCategoryEntity> getSubCategoriesByMainCategory(Long mainCategoryId) {
        return mainCategoryRepo.findById(mainCategoryId)
            .map(MainCategoryEntity::getSubCategories)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Main Category ID: " + mainCategoryId));
    }

	public int getIsSuccess(String userId) {
		Long themeId = themeRepository1.findActivatedThemeIdsByUserId(userId);
		return themeContentRepository.countAllByThemeIsSuccessTrue(themeId);
	}

}

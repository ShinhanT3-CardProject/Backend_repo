package com.shinhan.soloplay.theme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThemeContentRepository extends JpaRepository<ThemeContentEntity, Long> {

	@Query("SELECT COUNT(tc.themeIsSuccess) FROM ThemeContentEntity tc WHERE tc.themeIsSuccess = TRUE AND tc.theme.themeId = :themeId")
	int countAllByThemeIsSuccessTrue(Long themeId);
}

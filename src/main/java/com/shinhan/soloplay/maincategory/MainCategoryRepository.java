package com.shinhan.soloplay.maincategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MainCategoryRepository extends JpaRepository<MainCategoryEntity, Long> {

	 @Query("SELECT mc.themeMainCategoryName FROM MainCategoryEntity mc " +
	           "JOIN SubCategoryEntity sc ON mc = sc.mainCategory " +
	           "JOIN ThemeContentEntity tc ON sc = tc.subCategory " +
	           "JOIN ThemeEntity t ON tc.theme = t " +
	           "WHERE t.user.userId = :userId")
	    List<String> findMainCategoryNameByUserId(@Param("userId") String userId);
	
}

package com.shinhan.soloplay.theme;

import java.time.LocalDateTime;

import com.shinhan.soloplay.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeSearchDTO {
// 전체 및 나의 테마를 조회 및 상세조회 하기 위한 DTO
	
	//필요한 Entity 개별 항목들
	
	//테마 코드 : PK - ThemeEntity / t.themeId
	Long themeId;
	
	//유저 아이디 : FK - ThemeEntity / t.user
	UserEntity user;
	
	//테마 이름 - ThemeEntity / t.themeName
	String themeName;
	
	//테마 설명 - ThemeEntity / t.themeDescription
	String themeDescription;
	
	//테마 활성여부 - ThemeEntity / t.themeIsActivated
	Boolean themeIsActivated;
	
	//테마 공개여부 - ThemeEntity / t.themeIsPublic
	Boolean themeIsPublic;
	
	//테마 생성일 - ThemeEntity / t.themeCreateDate
	LocalDateTime themeCreateDate;
	
	//테마 수정일 - ThemeEntity / t.themeUpdateDate
	LocalDateTime themeUpdateDate;
	
	//테마 대분류 코드 : PK - MainCategoryEntity / mc.themeMainCategoryId
	Long themeMainCategoryId;
	
	//테마 대분류명 - MainCategoryEntity / mc.themeMainCategoryName
	String themeMainCategoryName;
	
	//테마 배경이미지 - MainCategoryEntity / mc.themeBackground
	String themeBackground;
	
	//테마 소분류명 - SubCategoryEntity / sc.themeSubCategoryName
	String themeSubCategoryName;
	
	// 필요 기능별 생성자
	
	// 전체 테마 조회용
	public ThemeSearchDTO(String themeName, String themeBackground) {
		super();
		this.themeName = themeName;
		this.themeBackground = themeBackground;
	}
	
	
	
	
	
}
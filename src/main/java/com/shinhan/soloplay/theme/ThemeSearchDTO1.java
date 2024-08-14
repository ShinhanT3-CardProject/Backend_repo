package com.shinhan.soloplay.theme;

import java.time.LocalDateTime;
import java.util.List;

import com.shinhan.soloplay.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeSearchDTO1 {
// 전체 및 나의 테마를 조회 및 상세조회 하기 위한 DTO
	
	//필요한 Entity 개별 항목들
	
	//테마 코드 : PK - ThemeEntity
	Long themeId;
	
	//유저 아이디 : FK - ThemeEntity
	UserEntity user;
	
	//테마 이름 - ThemeEntity
	String themeName;
	
	//테마 설명 - ThemeEntity
	String themeDescription;
	
	//테마 활성여부 - ThemeEntity
	Boolean themeIsActivated;
	
	//테마 공개여부 - ThemeEntity
	Boolean themeIsPublic;
	
	//테마 생성일 - ThemeEntity
	LocalDateTime themeCreateDate;
	
	//테마 수정일 - ThemeEntity
	LocalDateTime themeUpdateDate;
	
	//테마 컨텐츠 코드 연결 - ThemeEntity
	//@OneToMany(mappedBy = "theme")
	List<ThemeContentEntity> themeContents;
	
	
	//테마 컨텐츠 코드 : PK - ThemeContentEntity
	Long themeContentId;
	
	//테마 코드 : FK - ThemeContentEntity
	ThemeEntity theme;
	
	//테마 성공 여부 - ThemeContentEntity
	Boolean themeIsSuccess;
	
	//테마 소분류 코드 : FK - ThemeContentEntity
	SubCategoryEntity subCategory;
	
	
	//테마 소분류 코드 : PK - SubCategoryEntity
	Long themeSubCategoryId;
	
	//테마 대분류 코드 : FK - SubCategoryEntity
	MainCategoryEntity mainCategory;
	
	//테마 소분류명 - SubCategoryEntity
	String themeSubCategoryName;
	
	
	//테마 대분류 코드 : PK - MainCategoryEntity
	Long themeMainCategoryId;
	
	//테마 대분류명 - MainCategoryEntity
	String themeMainCategoryName;
	
	//테마 배경이미지 - MainCategoryEntity
	String themeBackground;
	
	//특정 기능을 위한 항목들
}



















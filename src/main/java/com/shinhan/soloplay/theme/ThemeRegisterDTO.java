package com.shinhan.soloplay.theme;

import java.time.LocalDateTime;
import java.util.List;

import com.shinhan.soloplay.maincategory.MainCategoryEntity;
import com.shinhan.soloplay.subcategory.SubCategoryEntity;
import com.shinhan.soloplay.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeRegisterDTO {
//나의 테마를 등록/수정/불러오기 하기 위한 DTO
	
	//필요한 Entity 개별 항목들
	
	//테마 코드 : PK - ThemeEntity
	Long themeId;
	
    // 유저 ID : FK - ThemeEntity (UserDTO로 변경)
    String userId;
	
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
	
	//테마 소분류 코드 : FK - ThemeContentEntity
	 private List<SubCategoryEntity> subCategory;
	
	//테마 대분류 코드 : FK - SubCategoryEntity
	MainCategoryEntity mainCategory;
	
}
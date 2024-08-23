package com.shinhan.soloplay.theme;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThemeServiceImpl1 implements ThemeService1 {
	
	@Autowired
	ThemeRepository1 themeRepository1;

	// 전체 테마 조회
	@Override
	public List<ThemeEntity> findAllTheme() {
		return themeRepository1.findAll();
	}
	
	// 전체 테마 조회 (카테고리별 필터링, 공개여부 참)
	@Override
	public List<ThemeSearchDTO1> findAllThemeFilter(Long themeMainCategoryId) {
		return themeRepository1.findAllThemeFilter(themeMainCategoryId);
	}
    
	// 테마 상세 조회
    @Override
    public ThemeSearchDTO1 findThemeDetail(Long themeId) {
    	return themeRepository1.findMyThemeDetail(themeId);
//    							.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 theme ID"));
    }
	
	// 나의 테마 조회
	@Override
	public List<ThemeSearchDTO1> findMyTheme(String userId) {
		return themeRepository1.findMyTheme(userId);
	}

	// 나의 테마 상세조회
    @Override
    public ThemeSearchDTO1 findMyThemeDetail(Long themeId) {
    	return findThemeDetail(themeId);
    }
    
	// 테마 수정 (나의 테마 상세조회에서 가능)
	@Override
	public ThemeRegisterDTO1 updateTheme(Long themeId, ThemeRegisterDTO1 themeRegisterDTO1) {
		ThemeEntity themeEntity = themeRepository1.findById(themeId)
												.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 theme ID"));
		//수정 진행에 대한 로직 구현
		themeEntity.setThemeName(themeRegisterDTO1.getThemeName());
		themeEntity.setThemeDescription(themeRegisterDTO1.getThemeDescription());
		themeEntity.setThemeIsPublic(themeRegisterDTO1.getThemeIsPublic());
		// 테마 대분류, 소분류 추가를 위해 Entity관련 작업을 해야 함 -> GPT 질문
		
		ThemeEntity updateTheme = themeRepository1.save(themeEntity);
		return convertToRegisterDTO(updateTheme);
	}
	
	// 테마 삭제 (나의 테마 상세조회에서 가능)
	@Override
	public void deleteTheme(Long themeId) {
		themeRepository1.deleteById(themeId);
	}
    
	// 테마 등록
	@Override
	public ThemeRegisterDTO1 insertTheme(ThemeRegisterDTO1 themeRegisterDTO1) {
	    // ThemeEntity 생성
	    ThemeEntity themeEntity = ThemeEntity.builder()
	            .themeName(themeRegisterDTO1.getThemeName())
	            .themeDescription(themeRegisterDTO1.getThemeDescription())
	            .themeIsActivated(themeRegisterDTO1.getThemeIsActivated())
	            .themeIsPublic(themeRegisterDTO1.getThemeIsPublic())
	            .user(themeRegisterDTO1.getUser())
	            .themeCreateDate(LocalDateTime.now())
	            .themeUpdateDate(LocalDateTime.now())
	            .build();

	    // SubCategoryEntity 리스트를 ThemeContentEntity로 변환하여 ThemeEntity에 연결
	    List<ThemeContentEntity> themeContents = themeRegisterDTO1.getSubCategory().stream()
	            .map(subCategory -> ThemeContentEntity.builder()
	                    .theme(themeEntity)  // ThemeEntity와 연결
	                    .subCategory(subCategory)  // SubCategoryEntity와 연결
	                    .themeIsSuccess(false)  // 초기 상태를 설정
	                    .build())
	            .collect(Collectors.toList());

	    // ThemeEntity에 ThemeContentEntity 리스트를 설정
	    themeEntity.setThemeContents(themeContents);

	    // ThemeEntity 저장
	    ThemeEntity savedTheme = themeRepository1.save(themeEntity);

	    // 저장된 ThemeEntity를 DTO로 변환
	    return convertToRegisterDTO(savedTheme);
	}

	
	// 테마 불러오기 (테마 등록, 테마 수정에서 가능)
	@Override
	public List<ThemeSearchDTO1> loadOtherTheme(Long themeId) {
		return themeRepository1.loadOtherTheme(themeId);
	}
	
	private ThemeSearchDTO1 convertToSearchDTO(ThemeEntity themeEntity) {
		// 첫 번째 ThemeContentEntity를 가져와서 사용
		ThemeContentEntity themeContentEntity = themeEntity.getThemeContents().stream().findFirst()
															.orElseThrow(() -> new IllegalStateException("ThemeContentEntity가 없습니다."));
		
		SubCategoryEntity subCategoryEntity = themeContentEntity.getSubCategory();
		MainCategoryEntity mainCategoryEntity = subCategoryEntity.getMainCategory();
		
		return ThemeSearchDTO1.builder()
								.themeId(themeEntity.getThemeId())
								.user(themeEntity.getUser())
								.themeName(themeEntity.getThemeName())
								.themeDescription(themeEntity.getThemeDescription())
								.themeIsActivated(themeEntity.getThemeIsActivated())
								.themeIsPublic(themeEntity.getThemeIsPublic())
								.themeCreateDate(themeEntity.getThemeCreateDate())
								.themeUpdateDate(themeEntity.getThemeUpdateDate())
								.themeMainCategoryId(mainCategoryEntity.getThemeMainCategoryId())
								.themeMainCategoryName(mainCategoryEntity.getThemeMainCategoryName())
								.themeBackground(mainCategoryEntity.getThemeBackground())
								.themeSubCategoryName(subCategoryEntity.getThemeSubCategoryName())
								.build();
	}
	
	private ThemeRegisterDTO1 convertToRegisterDTO(ThemeEntity themeEntity) {
	    List<ThemeContentEntity> themeContents = themeEntity.getThemeContents();

	    if (themeContents.isEmpty()) {
	        throw new IllegalStateException("ThemeContentEntity가 없습니다.");
	    }

	    // SubCategoryEntity에서 MainCategoryEntity의 themeBackground 가져오기
	    List<SubCategoryEntity> subCategories = themeContents.stream()
	            .map(ThemeContentEntity::getSubCategory)
	            .collect(Collectors.toList());

	    // 첫 번째 SubCategoryEntity를 사용해 MainCategoryEntity 참조
	    MainCategoryEntity mainCategoryEntity = subCategories.get(0).getMainCategory();
	    String themeBackground = (mainCategoryEntity != null) ? mainCategoryEntity.getThemeBackground() : null;

	    return ThemeRegisterDTO1.builder()
	            .themeId(themeEntity.getThemeId())
	            .user(themeEntity.getUser())
	            .themeName(themeEntity.getThemeName())
	            .themeDescription(themeEntity.getThemeDescription())
	            .themeIsActivated(themeEntity.getThemeIsActivated())
	            .themeIsPublic(themeEntity.getThemeIsPublic())
	            .themeCreateDate(themeEntity.getThemeCreateDate())
	            .themeUpdateDate(themeEntity.getThemeUpdateDate())
	            .subCategory(subCategories)
	            .mainCategory(mainCategoryEntity)
	            .build();
	}



	
	//Entity 변환 메서드 ThemeRegisterDTO1 -> ThemeEntity
	private ThemeEntity convertToEntity(ThemeRegisterDTO1 themeRegisterDTO1) {
	    // ThemeEntity 생성
	    ThemeEntity themeEntity = ThemeEntity.builder()
	            .themeName(themeRegisterDTO1.getThemeName())
	            .themeDescription(themeRegisterDTO1.getThemeDescription())
	            .themeIsActivated(themeRegisterDTO1.getThemeIsActivated())
	            .themeIsPublic(themeRegisterDTO1.getThemeIsPublic())
	            .user(themeRegisterDTO1.getUser())
	            .themeCreateDate(LocalDateTime.now())
	            .themeUpdateDate(LocalDateTime.now())
	            .build();

	    // SubCategoryEntity 리스트를 ThemeContentEntity로 변환하여 ThemeEntity에 연결
	    List<ThemeContentEntity> themeContents = themeRegisterDTO1.getSubCategory().stream()
	            .map(subCategory -> ThemeContentEntity.builder()
	                    .theme(themeEntity)  // ThemeEntity와 연결
	                    .subCategory(subCategory)  // SubCategoryEntity와 연결
	                    .themeIsSuccess(false)  // 초기 상태를 설정
	                    .build())
	            .collect(Collectors.toList());

	    // ThemeEntity에 ThemeContentEntity 리스트를 설정
	    themeEntity.setThemeContents(themeContents);

	    return themeEntity;
	}

}
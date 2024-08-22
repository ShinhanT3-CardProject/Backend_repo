package com.shinhan.soloplay.theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl1 implements ThemeService1 {
	
	private final ThemeRepository1 themeRepository1;

	// 전체 테마 조회 (공개여부 참) - 완료
	@Override
	public Map<Long, Map<String, String>> findAllTheme() {
		Map<Long, Map<String, String>> result = new HashMap<>();
		
		List<ThemeEntity> trueThemes = themeRepository1.findByThemeIsPublicTrue();
		Map<Long, ThemeContentEntity> contents = new HashMap<>();
		trueThemes.stream().forEach(theme->{
			theme.getThemeContents().stream().forEach(content->{
				contents.put(content.getThemeContentId(), content);
			});
			
			contents.keySet().stream().forEach(contentId->{
				Map<String, String> nameAndBack = new HashMap<>();
				nameAndBack.put("themeName", contents.get(contentId).getTheme().getThemeName());
				nameAndBack.put("themeBackground", contents.get(contentId).getSubCategory().getMainCategory().getThemeBackground());
		
				result.put(contents.get(contentId).getTheme().getThemeId(), nameAndBack);
			});
		});
		
		return result;
  }
	
	// 전체 테마 조회 (카테고리별 필터링)
	@Override
	public List<ThemeSearchDTO1> findAllThemeFilter(Long themeMainCategoryId) {
		return themeRepository1.findAllThemeFilter(themeMainCategoryId);
	}
    
	// 테마 상세 조회 - 블러오기까지는 완료, 복수의 테마를 담아오려면 조치 필요
    @Override
    public Map<String ,?> findThemeDetail(Long themeId) {
    	Map<String, Object> result = new HashMap<>(); //맨 마지막에 리턴할 값을 담아주는 용도
    	Map<Long, ThemeContentEntity> contents = new HashMap<>();
    	
    	ThemeEntity themeEntity = themeRepository1.findByThemeId(themeId);
    	result.put("themeName", themeEntity.getThemeName());
    	result.put("themeDescription", themeEntity.getThemeDescription());
    	
    	themeEntity.getThemeContents().stream().forEach(content -> {
    		contents.put(content.getThemeContentId(), content);
    	});
    	
    	List<String> subCategories = new ArrayList<>();
    	for(Long contentId:contents.keySet()) {
    		result.put("themeMainCategoryName", contents.get(contentId).getSubCategory().getMainCategory().getThemeMainCategoryName());
    		result.put("themeBackground", contents.get(contentId).getSubCategory().getMainCategory().getThemeBackground());
    		subCategories.add(contents.get(contentId).getSubCategory().getThemeSubCategoryName());
    	}
    	result.put("themeSunCategoryName", subCategories);
    	
    	return result;
    }
//    	contents.keySet().stream().forEach(contentId -> {
//    		
//    		result.put("themeMainCategoryName", contents.get(contentId).getSubCategory().getMainCategory().getThemeMainCategoryName());
//    		result.put("themeBackground", contents.get(contentId).getSubCategory().getMainCategory().getThemeBackground());
//    		result.put("themeSunCategoryName", contents.get(contentId).getSubCategory().getThemeSubCategoryName());
//    		
//    	});
	
	// 나의 테마 조회
	@Override
	public List<ThemeSearchDTO1> findMyTheme(String userId) {
		return themeRepository1.findMyTheme(userId);
	}

	// 나의 테마 상세조회
    @Override
    public ThemeSearchDTO1 findMyThemeDetail(Long themeId) {
    	return findMyThemeDetail(themeId);
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
		ThemeEntity themeEntity = convertToEntity(themeRegisterDTO1);
		ThemeEntity savedTheme = themeRepository1.save(themeEntity);
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
		// 첫 번째 ThemeContentEntity를 가져와서 사용
		ThemeContentEntity themeContentEntity = themeEntity.getThemeContents().stream().findFirst()
															.orElseThrow(() -> new IllegalStateException("ThemeContentEntity가 없습니다."));
		
		SubCategoryEntity subCategoryEntity = themeContentEntity.getSubCategory();
		MainCategoryEntity mainCategoryEntity = subCategoryEntity.getMainCategory();
		
		return ThemeRegisterDTO1.builder()
								.themeId(themeEntity.getThemeId())
								.user(themeEntity.getUser())
								.themeName(themeEntity.getThemeName())
								.themeDescription(themeEntity.getThemeDescription())
								.themeIsActivated(themeEntity.getThemeIsActivated())
								.themeIsPublic(themeEntity.getThemeIsPublic())
								.themeCreateDate(themeEntity.getThemeCreateDate())
								.themeUpdateDate(themeEntity.getThemeUpdateDate())
								.subCategory(subCategoryEntity)
								.mainCategory(mainCategoryEntity)
								.themeBackground(mainCategoryEntity.getThemeBackground())
                                .build();
	}
	
	//Entity 변환 메서드 ThemeRegisterDTO1 -> ThemeEntity
	private ThemeEntity convertToEntity(ThemeRegisterDTO1 themeRegisterDTO1) {
		// 새로운 ThemeEntity 생성
		ThemeEntity themeEntity = ThemeEntity.builder()
											.themeId(themeRegisterDTO1.getThemeId())
											.user(themeRegisterDTO1.getUser())
											.themeName(themeRegisterDTO1.getThemeName())
											.themeDescription(themeRegisterDTO1.getThemeDescription())
											.themeIsActivated(themeRegisterDTO1.getThemeIsActivated())
											.themeIsPublic(themeRegisterDTO1.getThemeIsPublic())
											.themeCreateDate(themeRegisterDTO1.getThemeCreateDate())
											.themeUpdateDate(themeRegisterDTO1.getThemeUpdateDate())
											.build();

		//ThemeContentEntity 생성 및 연결
		ThemeContentEntity themeContentEntity = ThemeContentEntity.builder()
//																	.theme(themeEntity)
																	.subCategory(themeRegisterDTO1.getSubCategory())
																	.build();
		themeEntity.setThemeContents(List.of(themeContentEntity));
		
		return themeEntity;
	}
}
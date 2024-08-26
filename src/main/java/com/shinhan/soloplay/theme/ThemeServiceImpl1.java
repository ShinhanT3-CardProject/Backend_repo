package com.shinhan.soloplay.theme;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
				nameAndBack.put("themeMainCategoryName", contents.get(contentId).getSubCategory().getMainCategory().getThemeMainCategoryName());
		
				result.put(contents.get(contentId).getTheme().getThemeId(), nameAndBack);
			});
		});
		
		return result;
  }
    
	// 테마 상세 조회, 나의 테마 상세조회 - 완료
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
    		
    		result.put("themeIsActivated", contents.get(contentId).getTheme().getThemeIsActivated());
    		result.put("themeIsPublic", contents.get(contentId).getTheme().getThemeIsPublic());
    		
    		subCategories.add(contents.get(contentId).getSubCategory().getThemeSubCategoryName());
    	}
    	result.put("themeSubCategoryName", subCategories);
    	
    	return result;
    }

//    for문의 다른 방법
//    	contents.keySet().stream().forEach(contentId -> {
//    		
//    		result.put("themeMainCategoryName", contents.get(contentId).getSubCategory().getMainCategory().getThemeMainCategoryName());
//    		result.put("themeBackground", contents.get(contentId).getSubCategory().getMainCategory().getThemeBackground());
//    		result.put("themeSunCategoryName", contents.get(contentId).getSubCategory().getThemeSubCategoryName());
//    		
//    	});
	
    // 나의 테마 조회 - 완료
    @Override
	public Map<Long, Map<String, String>> findMyTheme(String userId) {
		Map<Long, Map<String, String>> result = new HashMap<>();
		
		List<ThemeEntity> myThemes = themeRepository1.findByUser_UserId(userId);
		System.out.println("myThemes : " + myThemes);
		Map<Long, ThemeContentEntity> contents = new HashMap<>();
		myThemes.stream().forEach(theme->{
			
			if (theme.getThemeContents().isEmpty()) {
				System.out.println("themeContents가 비어있습니다.");
			}
			
			theme.getThemeContents().stream().forEach(content -> {
				contents.put(content.getThemeContentId(), content);
			});
			
			contents.keySet().stream().forEach(contentId -> {
				Map<String, String> nameAndBack = new HashMap<>();
				nameAndBack.put("themeName", contents.get(contentId).getTheme().getThemeName());
				nameAndBack.put("themeBackground", contents.get(contentId).getSubCategory().getMainCategory().getThemeBackground());
				nameAndBack.put("themeMainCategoryName", contents.get(contentId).getSubCategory().getMainCategory().getThemeMainCategoryName());
				
				result.put(contents.get(contentId).getTheme().getThemeId(), nameAndBack);
			});
		});
		System.out.println("result : " + result);
		
		return result;
	}
    
	// 테마 수정 (나의 테마 상세조회에서 가능) - Postman까지 테스트 완료, Front 연결 중
	@Override
	public ThemeRegisterDTO1 updateTheme(Long themeId, ThemeRegisterDTO1 themeRegisterDTO1) {
		ThemeEntity themeEntity = themeRepository1.findById(themeId)
												.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 theme ID"));
		
		themeEntity.setThemeName(themeRegisterDTO1.getThemeName());
		themeEntity.setThemeDescription(themeRegisterDTO1.getThemeDescription());
		themeEntity.setThemeIsPublic(themeRegisterDTO1.getThemeIsPublic());
		themeEntity.setThemeIsActivated(themeRegisterDTO1.getThemeIsActivated());
		themeEntity.setThemeUpdateDate(LocalDateTime.now());
		
		List<ThemeContentEntity> updateContents = themeEntity.getThemeContents();
		List<SubCategoryEntity> updateCategories = themeRegisterDTO1.getSubCategory();
		
		int contentSize = updateContents.size();
		int CategorySize = updateCategories.size();
		
		for(int i = 0; i < contentSize && i < CategorySize; i++) {
			ThemeContentEntity content = updateContents.get(i);
			content.setSubCategory(updateCategories.get(i));
		}
		
		if (CategorySize > contentSize) {
			for (int i = contentSize ; i < CategorySize; i++) {
				ThemeContentEntity updatedContents = ThemeContentEntity.builder()
																		.theme(themeEntity)
																		.subCategory(updateCategories.get(i))
																		.themeIsSuccess(false)
																		.build();
				updateContents.add(updatedContents);
			}
		}
		
		// 설정된 Entity 저장
		ThemeEntity updateTheme = themeRepository1.save(themeEntity);
		return convertToRegisterDTO(updateTheme);
	}
	
	// 테마 삭제 (나의 테마 상세조회에서 가능) - 완료
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
	
	//없어도 되는지 실험
	
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
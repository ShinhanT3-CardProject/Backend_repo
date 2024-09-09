package com.shinhan.soloplay.theme;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shinhan.soloplay.maincategory.MainCategoryEntity;
import com.shinhan.soloplay.maincategory.MainCategoryRepository;
import com.shinhan.soloplay.subcategory.SubCategoryEntity;
import com.shinhan.soloplay.themecontent.ThemeContentEntity;
import com.shinhan.soloplay.themecontent.ThemeContentRepository;
import com.shinhan.soloplay.themecontent.ThemeContentsResponseDTO;
import com.shinhan.soloplay.user.UserEntity;
import com.shinhan.soloplay.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {
	
	private final UserRepository userRepository;
	final MainCategoryRepository mainCategoryRepo;
    final ThemeRepository themeRepository;
	final ThemeContentRepository themeContentRepository;
	

	// 전체 테마 조회 (공개여부 참) - 완료
//	@Override
//	public List<ThemeDetailResponseDTO> findAllTheme() {
//	    List<ThemeEntity> trueThemes = themeRepository.findByThemeIsPublicTrue();
//
//	    return trueThemes.stream().map(themeEntity -> {
//	        MainCategoryEntity mainCategory = themeEntity.getThemeContents()
//	                .get(0)
//	                .getSubCategory()
//	                .getMainCategory();
//
//	        return ThemeDetailResponseDTO.builder()
//	        		.themeId(themeEntity.getThemeId())
//	                .themeName(themeEntity.getThemeName())
//	                .themeMainCategoryName(mainCategory.getThemeMainCategoryName())
//	                .themeBackground(mainCategory.getThemeBackground())
//	                .build();
//	    }).collect(Collectors.toList());
//	}
	
	// 전체 테마 조회 (공개여부 참) - 완료
	@Override
	public Page<ThemeDetailResponseDTO> findAllTheme(int page) {
	    Pageable pageable = PageRequest.of(page, 5);
	    
	    Page<ThemeEntity> themeEntities = themeRepository.findAllTheme(pageable);
	    
	    List<ThemeDetailResponseDTO> dtoList = themeEntities.stream().map(themeEntity -> {
	        MainCategoryEntity mainCategory = themeEntity.getThemeContents()
	                .get(0)
	                .getSubCategory()
	                .getMainCategory();
	        
	        return ThemeDetailResponseDTO.builder()
	                .themeId(themeEntity.getThemeId())
	                .themeName(themeEntity.getThemeName())
	                .themeMainCategoryName(mainCategory.getThemeMainCategoryName())
	                .themeBackground(mainCategory.getThemeBackground())
	                .build();
	    }).collect(Collectors.toList());

	    // Page<ThemeDetailResponseDTO> 객체를 생성하여 반환
	    return new PageImpl<>(dtoList, pageable, themeEntities.getTotalElements());
	}
	
	// 카테고리별 테마 조회 - 페이징
	public Page<ThemeDetailResponseDTO> findByCategory(int page, Long themeMainCategoryId) {
		Pageable pageable = PageRequest.of(page, 5);
		Page<ThemeEntity> themeEntities = themeRepository.findByCategory(pageable, themeMainCategoryId);
		
		List<ThemeDetailResponseDTO> dtoList = themeEntities.stream().map(themeEntity -> {
	        MainCategoryEntity mainCategory = themeEntity.getThemeContents()
	                .get(0)
	                .getSubCategory()
	                .getMainCategory();
	        
	        return ThemeDetailResponseDTO.builder()
	                .themeId(themeEntity.getThemeId())
	                .themeName(themeEntity.getThemeName())
	                .themeMainCategoryName(mainCategory.getThemeMainCategoryName())
	                .themeBackground(mainCategory.getThemeBackground())
	                .build();
	    }).collect(Collectors.toList());

	    // Page<ThemeDetailResponseDTO> 객체를 생성하여 반환
	    return new PageImpl<>(dtoList, pageable, themeEntities.getTotalElements());
	}
			
	// 테마 검색 - 페이징
	public Page<ThemeDetailResponseDTO> searchByName(int page, String search){ 
		search = "%" + search.toLowerCase() + "%";
		Pageable pageable = PageRequest.of(page, 5);
		Page<ThemeEntity> themeEntities = themeRepository.searchByName(pageable, search);
		
		List<ThemeDetailResponseDTO> dtoList = themeEntities.stream().map(themeEntity -> {
	        MainCategoryEntity mainCategory = themeEntity.getThemeContents()
	                .get(0)
	                .getSubCategory()
	                .getMainCategory();
	        
	        return ThemeDetailResponseDTO.builder()
	                .themeId(themeEntity.getThemeId())
	                .themeName(themeEntity.getThemeName())
	                .themeMainCategoryName(mainCategory.getThemeMainCategoryName())
	                .themeBackground(mainCategory.getThemeBackground())
	                .build();
	    }).collect(Collectors.toList());

	    // Page<ThemeDetailResponseDTO> 객체를 생성하여 반환
	    return new PageImpl<>(dtoList, pageable, themeEntities.getTotalElements());
	}

    
	// 테마 상세 조회, 나의 테마 상세조회 - 완료
    @Override
    public ThemeDetailResponseDTO findThemeDetail(Long themeId) {
    	
    	ThemeEntity themeEntity = themeRepository.findByThemeId(themeId);
    	
    	MainCategoryEntity mainCategory = themeEntity.getThemeContents()
			.get(0)
			.getSubCategory()
			.getMainCategory();
    	
    	String themeMainCategoryName = mainCategory.getThemeMainCategoryName();
    	
    	String themeBackground = mainCategory.getThemeBackground();
    	
    	List<ThemeContentsResponseDTO> contentsStore = themeEntity.getThemeContents()
    		    .stream()
    		    .map(contents -> ThemeContentsResponseDTO.builder()
    		            .themeIsSuccess(contents.getThemeIsSuccess())
    		            .themeSubCategoryName(contents.getSubCategory().getThemeSubCategoryName())
    		            .themeSubCategoryId(contents.getSubCategory().getThemeSubCategoryId())
    		            .themeContentId(contents.getThemeContentId())
    		            .build()
    		    )
    		    .collect(Collectors.toList());
    	
    	ThemeDetailResponseDTO themeInfo = ThemeDetailResponseDTO.builder()
    			.themeId(themeEntity.getThemeId())
    			.themeName(themeEntity.getThemeName())
    			.themeDescription(themeEntity.getThemeDescription())
    			.themeMainCategoryName(themeMainCategoryName)
    			.themeIsActivated(themeEntity.getThemeIsActivated())
    			.themeIsPublic(themeEntity.getThemeIsPublic())
    			.themeBackground(themeBackground)
    			.themeContents(contentsStore)

    			.build();
    	
    	
    	return themeInfo;
    }
	
    // 나의 테마 조회 - 완료
    @Override
	public List<ThemeDetailResponseDTO>findMyTheme(String userId) {
	    List<ThemeEntity> myThemes = themeRepository.findByUser_UserId(userId);

	    return myThemes.stream().map(themeEntity -> {
	        MainCategoryEntity mainCategory = themeEntity.getThemeContents()
	                .get(0)
	                .getSubCategory()
	                .getMainCategory();

	        return ThemeDetailResponseDTO.builder()
	        		.themeId(themeEntity.getThemeId())
	                .themeName(themeEntity.getThemeName())
	                .themeMainCategoryName(mainCategory.getThemeMainCategoryName())
	                .themeBackground(mainCategory.getThemeBackground())
	                .build();
	    }).collect(Collectors.toList());
	}
    
	// 테마 수정 (나의 테마 상세조회에서 가능) - Postman까지 테스트 완료, Front 연결 중
	@Override
	public ThemeRegisterDTO updateTheme(Long themeId, ThemeRegisterDTO themeRegisterDTO1, String userId) {
		ThemeEntity themeEntity = themeRepository.findById(themeId)
												.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 theme ID"));
		
		if(Boolean.TRUE.equals(themeRegisterDTO1.getThemeIsActivated())) {
			themeRepository.findThemeIsActivated(userId);
		}
		
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
		ThemeEntity updateTheme = themeRepository.save(themeEntity);
		return convertToRegisterDTO(updateTheme);
	}
	
	// 테마 삭제 (나의 테마 상세조회에서 가능) - 완료
	@Override
	public void deleteTheme(Long themeId) {
		themeRepository.deleteById(themeId);
	}
    
	// 테마 등록
	@Override
	public ThemeRegisterDTO insertTheme(ThemeRegisterDTO themeRegisterDTO1) {
		 // UserEntity 조회
	    UserEntity userEntity = userRepository.findById(themeRegisterDTO1.getUserId())
	                                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID"));

	    // ThemeEntity 생성
	    ThemeEntity themeEntity = ThemeEntity.builder()
	            .themeName(themeRegisterDTO1.getThemeName())
	            .themeDescription(themeRegisterDTO1.getThemeDescription())
	            .themeIsActivated(themeRegisterDTO1.getThemeIsActivated())
	            .themeIsPublic(themeRegisterDTO1.getThemeIsPublic())
	            .user(userEntity)
	            .themeCreateDate(LocalDateTime.now())
	            .themeUpdateDate(LocalDateTime.now())
	            .build();

	    // SubCategoryEntity 리스트를 ThemeContentEntity로 변환하여 ThemeEntity에 연결
	    List<ThemeContentEntity> themeContents = themeRegisterDTO1.getSubCategory().stream()
	            .map(subCategory -> ThemeContentEntity.builder()
	                    .theme(themeEntity)  // ThemeEntity와 연결
	                    .subCategory(subCategory)  // SubCategoryEntity와 연결
	                    .themeIsSuccess(false)  // 초기 상태를 설정
	                    .themeContentIsRewarded(false)
	                    .build())
	            .collect(Collectors.toList());

	    // ThemeEntity에 ThemeContentEntity 리스트를 설정
	    themeEntity.setThemeContents(themeContents);

	    // ThemeEntity 저장
	    ThemeEntity savedTheme = themeRepository.save(themeEntity);

	    // 저장된 ThemeEntity를 DTO로 변환
	    return convertToRegisterDTO(savedTheme);
	}
	
	// 테마 보상 대상 여부 확인
	@Override
	public boolean checkThemeSuccess(Long themeId) {
		return !themeRepository.findById(themeId).get().getThemeIsRewarded()
					&& themeContentRepository.countAllByThemeIsSuccessTrue(themeId) == 5;
	}
	
	// 테마 성공 보상 수여
	@Override
	public int updateThemeIsRewarded(Long themeId) {
		return themeRepository.updateThemeIsRewarded(themeId);
	}
	
	public List<MainCategoryEntity> getAllMainCategories() {
        return mainCategoryRepo.findAll();
    }

    public List<SubCategoryEntity> getSubCategoriesByMainCategory(Long mainCategoryId) {
        return mainCategoryRepo.findById(mainCategoryId)
            .map(MainCategoryEntity::getSubCategories)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Main Category ID: " + mainCategoryId));
    }

	public int getIsSuccess(String userId) {
		Long themeId = themeRepository.findActivatedThemeIdsByUserId(userId);
		return themeContentRepository.countAllByThemeIsSuccessTrue(themeId);
	}

	
	public void getIsActivated(String userId) {
		themeRepository.findThemeIsActivated(userId);
	}

	public Long getThemeCount(String userId) {
		return themeRepository.findThemeCount(userId);
	}

	
	
	//없어도 되는지 실험
	
//	private ThemeSearchDTO convertToSearchDTO(ThemeEntity themeEntity) {
//		// 첫 번째 ThemeContentEntity를 가져와서 사용
//		ThemeContentEntity themeContentEntity = themeEntity.getThemeContents().stream().findFirst()
//															.orElseThrow(() -> new IllegalStateException("ThemeContentEntity가 없습니다."));
//		
//		SubCategoryEntity subCategoryEntity = themeContentEntity.getSubCategory();
//		MainCategoryEntity mainCategoryEntity = subCategoryEntity.getMainCategory();
//		
//		return ThemeSearchDTO.builder()
//								.themeId(themeEntity.getThemeId())
//								.user(themeEntity.getUser())
//								.themeName(themeEntity.getThemeName())
//								.themeDescription(themeEntity.getThemeDescription())
//								.themeIsActivated(themeEntity.getThemeIsActivated())
//								.themeIsPublic(themeEntity.getThemeIsPublic())
//								.themeCreateDate(themeEntity.getThemeCreateDate())
//								.themeUpdateDate(themeEntity.getThemeUpdateDate())
//								.themeMainCategoryId(mainCategoryEntity.getThemeMainCategoryId())
//								.themeMainCategoryName(mainCategoryEntity.getThemeMainCategoryName())
//								.themeBackground(mainCategoryEntity.getThemeBackground())
//								.themeSubCategoryName(subCategoryEntity.getThemeSubCategoryName())
//								.build();
//	}
	
	private ThemeRegisterDTO convertToRegisterDTO(ThemeEntity themeEntity) {
	    List<ThemeContentEntity> themeContents = themeEntity.getThemeContents();

	    if (themeContents.isEmpty()) {
	        throw new IllegalStateException("ThemeContentEntity가 없습니다.");
	    }

	    // SubCategoryEntity 리스트를 추출
	    List<SubCategoryEntity> subCategories = themeContents.stream()
	            .map(ThemeContentEntity::getSubCategory)
	            .collect(Collectors.toList());

	    // 첫 번째 SubCategoryEntity를 사용해 MainCategoryEntity 참조
	    MainCategoryEntity mainCategoryEntity = subCategories.get(0).getMainCategory();

	    // UserEntity에서 필요한 정보만 추출 (또는 UserDTO로 변환)
	    String userId = themeEntity.getUser().getUserId();

	    return ThemeRegisterDTO.builder()
	            .themeId(themeEntity.getThemeId())
	            .userId(userId)  // UserEntity 대신 userId를 사용하거나 UserDTO로 변환할 수 있습니다.
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
//	private ThemeEntity convertToEntity(ThemeRegisterDTO themeRegisterDTO1) {
//		
//	    // UserEntity 조회
//	    UserEntity userEntity = userRepository.findById(themeRegisterDTO1.getUserId())
//	                                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID"));
//
//	    // ThemeEntity 생성
//	    ThemeEntity themeEntity = ThemeEntity.builder()
//	            .themeName(themeRegisterDTO1.getThemeName())
//	            .themeDescription(themeRegisterDTO1.getThemeDescription())
//	            .themeIsActivated(themeRegisterDTO1.getThemeIsActivated())
//	            .themeIsPublic(themeRegisterDTO1.getThemeIsPublic())
//	            .user(userEntity)
//	            .themeCreateDate(LocalDateTime.now())
//	            .themeUpdateDate(LocalDateTime.now())
//	            .build();
//
//	    // SubCategoryEntity 리스트를 ThemeContentEntity로 변환하여 ThemeEntity에 연결
//	    List<ThemeContentEntity> themeContents = themeRegisterDTO1.getSubCategory().stream()
//	            .map(subCategory -> ThemeContentEntity.builder()
//	                    .theme(themeEntity)  // ThemeEntity와 연결
//	                    .subCategory(subCategory)  // SubCategoryEntity와 연결
//	                    .themeIsSuccess(false)  // 초기 상태를 설정
//	                    .build())
//	            .collect(Collectors.toList());
//
//	    // ThemeEntity에 ThemeContentEntity 리스트를 설정
//	    themeEntity.setThemeContents(themeContents);
//
//	    return themeEntity;
//	}


}
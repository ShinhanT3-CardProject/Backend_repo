package com.shinhan.soloplay.themecontent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThemeContentsResponseDTO {
	
	Boolean themeIsSuccess;
	
	String themeSubCategoryName;
	
	Long themeSubCategoryId;
	
	Long themeContentId;
}

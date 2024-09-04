package com.shinhan.soloplay.theme;

import java.util.List;

import com.shinhan.soloplay.themecontent.ThemeContentsResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThemeDetailResponseDTO {
	
	
	Long themeId;
	
	String themeName;
	
	String themeDescription;
	
	String themeMainCategoryName;
	
	String themeBackground;
	
	Boolean themeIsActivated;
	
	Boolean themeIsPublic;
	
	List<ThemeContentsResponseDTO> themeContents;
}

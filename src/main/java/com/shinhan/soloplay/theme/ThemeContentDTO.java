package com.shinhan.soloplay.theme;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThemeContentDTO {
	Long themeContentId;
	Long themeId;
	Boolean themeIsSuccess;
	Long themeSubCategoryId;
}

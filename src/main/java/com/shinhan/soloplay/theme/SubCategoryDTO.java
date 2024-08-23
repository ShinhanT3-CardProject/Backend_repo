package com.shinhan.soloplay.theme;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryDTO {
    private Long themeSubCategoryId;
    private String themeSubCategoryName;
    private String themeMainCategoryName;
}

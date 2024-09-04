package com.shinhan.soloplay.merchant;

import com.shinhan.soloplay.subcategory.SubCategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantDTO {
	
	String merchantID;
	String merchantAddress;
	String merchantName;
	SubCategoryEntity subCategory;
}

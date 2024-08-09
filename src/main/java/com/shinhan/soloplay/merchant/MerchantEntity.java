package com.shinhan.soloplay.merchant;

import com.shinhan.soloplay.theme.SubCategoryEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MERCHANT")
public class MerchantEntity {

    @Id
    @Column(name = "MERCHANT_ID", nullable = false, length = 255)
    private String merchantId;

    @Column(name = "MERCHANT_ADDRESS", nullable = true, length = 255)
    private String merchantAddress;

    @Column(name = "SUB_CATEGORY_ID", nullable = false)
    private Integer subCategoryId;
    
    @ManyToOne
    @JoinColumn(name = "themeSubCategoryId")
    private SubCategoryEntity subCategory;
    
}

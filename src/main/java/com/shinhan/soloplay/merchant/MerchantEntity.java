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
    @Column(name = "MERCHANT_ID")
    private String merchantId;

    @Column(name = "MERCHANT_ADDRESS")
    private String merchantAddress;

    @ManyToOne
    @JoinColumn(name = "THEME_SUB_CATEGORY_ID")
    private SubCategoryEntity subCategory;
    
}

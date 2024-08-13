package com.shinhan.soloplay.theme;

import java.util.List;

import com.shinhan.soloplay.merchant.MerchantEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "SUB_CATEGORY")
public class SubCategoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "THEME_SUB_CATEGORY_ID")
    private Integer themeSubCategoryId;

    @ManyToOne
    @JoinColumn(name = "THEME_MAIN_CATEGORY_ID")
    private MainCategoryEntity mainCategory;

    @Column(name = "THEME_SUB_CATEGORY_NAME")
    private String themeSubCategoryName;

    @OneToMany(mappedBy = "subCategory")
    private List<MerchantEntity> merchants;

    // Getters and Setters
}
package com.shinhan.soloplay.maincategory;

import java.util.List;

import com.shinhan.soloplay.subcategory.SubCategoryEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "MAIN_CATEGORY")
@ToString(exclude = "subCategories")
public class MainCategoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "THEME_MAIN_CATEGORY_ID")
    private Long themeMainCategoryId;

    @Column(name = "THEME_MAIN_CATEGORY_NAME")
    private String themeMainCategoryName;

    @Column(name = "THEME_BACKGROUND")
    private String themeBackground;

    @OneToMany(mappedBy = "mainCategory", fetch = FetchType.LAZY)
    private List<SubCategoryEntity> subCategories;

}
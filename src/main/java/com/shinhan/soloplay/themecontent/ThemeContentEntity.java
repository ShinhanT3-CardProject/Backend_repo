package com.shinhan.soloplay.themecontent;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shinhan.soloplay.subcategory.SubCategoryEntity;
import com.shinhan.soloplay.theme.ThemeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = "theme")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "THEME_CONTENT")
public class ThemeContentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "THEME_CONTENT_ID")
    private Long themeContentId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "THEME_ID")
    private ThemeEntity theme;

    @Column(name = "THEME_IS_SUCCESS")
    private Boolean themeIsSuccess;

    @OneToOne
    @JoinColumn(name = "THEME_SUB_CATEGORY_ID")
    private SubCategoryEntity subCategory;
    
    @Column(name = "THEME_CONTENT_IS_REWARDED", columnDefinition = "TINYINT(1)")
    @ColumnDefault("false")
    private Boolean themeContentIsRewarded;

}

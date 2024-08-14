package com.shinhan.soloplay.theme;

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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "THEME_CONTENT")
@ToString(exclude = "theme")
public class ThemeContentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "THEME_CONTENT_ID")
    private Long themeContentId;

    @ManyToOne
    @JoinColumn(name = "THEME_ID")
    private ThemeEntity theme;

    @Column(name = "THEME_IS_SUCCESS")
    private Boolean themeIsSuccess;

    @OneToOne
    @JoinColumn(name = "THEME_SUB_CATEGORY_ID")
    private SubCategoryEntity subCategory;

    // Getters and Setters
}

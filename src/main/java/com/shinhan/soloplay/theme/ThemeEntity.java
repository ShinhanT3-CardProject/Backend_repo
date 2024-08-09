package com.shinhan.soloplay.theme;

import java.time.LocalDateTime;
import java.util.List;

import com.shinhan.soloplay.auth.AuthEntity;

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
@Table(name = "THEME")
public class ThemeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "THEME_ID")
    private Integer themeId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private AuthEntity user;

    @Column(name = "THEME_NAME", nullable = false)
    private String themeName;

    @Column(name = "THEME_DESCRIPTION")
    private String themeDescription;

    @Column(name = "THEME_IS_ACTIVATED")
    private Boolean themeIsActivated;

    @Column(name = "THEME_IS_PUBLIC")
    private Boolean themeIsPublic;

    @Column(name = "THEME_CREATE_DATE")
    private LocalDateTime themeCreateDate;

    @Column(name = "THEME_UPDATE_DATE")
    private LocalDateTime themeUpdateDate;

    @OneToMany(mappedBy = "theme")
    private List<ThemeContentEntity> themeContents;

    // Getters and Setters
}

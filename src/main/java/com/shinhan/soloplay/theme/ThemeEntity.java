package com.shinhan.soloplay.theme;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.shinhan.soloplay.themecontent.ThemeContentEntity;
import com.shinhan.soloplay.user.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import lombok.ToString;

@ToString(exclude = {"user", "themeContents"})
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
    private Long themeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

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
    
    @Column(name = "THEME_IS_REWARDED", columnDefinition = "TINYINT(1)")
    @ColumnDefault("false")
    private Boolean themeIsRewarded;

    @OneToMany(mappedBy = "theme", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ThemeContentEntity> themeContents;

}

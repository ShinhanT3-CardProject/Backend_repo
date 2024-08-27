package com.shinhan.soloplay.theme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ThemeRepositoryJK extends JpaRepository<ThemeEntity, Long> {
	
    // 유저 ID와 활성화된 테마에 해당하는 테마 ID를 찾는 쿼리
    @Query("SELECT t.themeId FROM ThemeEntity t WHERE t.user.userId = :userId AND t.themeIsActivated = true")
    Long findActivatedThemeIdsByUserId(@Param("userId") String userId);
    
    //유저 ID에 대한 테마 전부 0으로 두기
//    @Query("UPDATE ThemeEntity t set t.theme_is_activated = 0 where t.user.user_id = :userId")
//    Long findThemeIsActivated(@Param("userId") String userId);
    
    //유저 ID에 대한 등록된 테마 갯수 확인
    @Query("SELECT count(t.user.userId) from ThemeEntity t where t.user.userId= :userId")
    Long findThemeCount(@Param("userId") String userId);
}

package com.shinhan.soloplay.card;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.soloplay.user.UserEntity;

public interface UserCardRepository extends CrudRepository<UserCardEntity, String> {
   List<UserCardEntity> findByUser(UserEntity user);
}

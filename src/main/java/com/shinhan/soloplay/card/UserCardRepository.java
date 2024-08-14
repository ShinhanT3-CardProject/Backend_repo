package com.shinhan.soloplay.card;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserCardRepository extends CrudRepository<UserCardEntity, String> {
    List<UserCardEntity> findByUserUserId(String userId);
}

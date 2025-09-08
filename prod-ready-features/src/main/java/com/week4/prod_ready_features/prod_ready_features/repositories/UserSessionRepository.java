package com.week4.prod_ready_features.prod_ready_features.repositories;


import com.week4.prod_ready_features.prod_ready_features.entities.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Long> {

    Optional<UserSessionEntity> findBySessionTokenAndUserId(String sessionToken, Long userId);

    void deleteByUserId(Long userId);
}

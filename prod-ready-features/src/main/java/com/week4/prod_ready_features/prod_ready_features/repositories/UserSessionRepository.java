package com.week4.prod_ready_features.prod_ready_features.repositories;


import com.week4.prod_ready_features.prod_ready_features.entities.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Long> {

    Optional<UserSessionEntity> findByRefreshTokenAndUserId(String sessionToken, Long userId);

    List<UserSessionEntity> findByUserId(Long userId);

    Optional<UserSessionEntity> findByRefreshToken(String refreshToken);

    void deleteByUserId(Long userId);

    void deleteByRefreshToken(String refreshToken);

    void deleteByRefreshTokenAndUserId(String refreshToken, Long userId);

    int countByUserId(Long userId);
}

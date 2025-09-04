package com.week4.prod_ready_features.prod_ready_features.repositories;

import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}

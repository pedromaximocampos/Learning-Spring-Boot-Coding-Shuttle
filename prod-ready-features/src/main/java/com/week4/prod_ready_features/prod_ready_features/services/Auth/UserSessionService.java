package com.week4.prod_ready_features.prod_ready_features.services.Auth;


import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.entities.UserSessionEntity;
import com.week4.prod_ready_features.prod_ready_features.repositories.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;
    private static final int MAX_SESSIONS = 2;

    public void validateSession(String sessionToken){
        UserSessionEntity session =  userSessionRepository.findByRefreshToken(sessionToken)
                .orElseThrow(() -> new SessionAuthenticationException("Session not found exception"));

        session.setLastLoginAt(LocalDateTime.now());
        userSessionRepository.save(session);
    }

    @Transactional
    public void createNewUserSession(String refreshToken, UserEntity user){
        List<UserSessionEntity> existingSessions = userSessionRepository.findByUserId(user.getId());

        if(existingSessions.size() == MAX_SESSIONS){
            existingSessions.sort(Comparator.comparing(UserSessionEntity::getLastLoginAt));
            UserSessionEntity oldestSession = existingSessions.getFirst();

            userSessionRepository.delete(oldestSession);
        }

        UserSessionEntity userSessionEntity = new UserSessionEntity();
        userSessionEntity.setRefreshToken(refreshToken);
        userSessionEntity.setUser(user);

        userSessionRepository.save(userSessionEntity);
    }

    public void deleteSession(String refreshToken, Long userId) {
        UserSessionEntity session  = userSessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("Session not found exception"));
        userSessionRepository.deleteByRefreshTokenAndUserId(refreshToken, userId);
    }
}

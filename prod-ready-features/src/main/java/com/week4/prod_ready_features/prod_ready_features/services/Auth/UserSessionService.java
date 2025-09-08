package com.week4.prod_ready_features.prod_ready_features.services.Auth;


import com.week4.prod_ready_features.prod_ready_features.entities.UserSessionEntity;
import com.week4.prod_ready_features.prod_ready_features.repositories.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;


    public Boolean isSessionValid(String sessionToken, Long userId){
        return userSessionRepository.findBySessionTokenAndUserId(sessionToken, userId).isPresent();
    }

    public void invalidateSession(String sessionToken, Long userId){
        userSessionRepository.findBySessionTokenAndUserId(sessionToken, userId)
                .ifPresent(userSessionRepository::delete);
    }

    @Transactional
    public void createNewUserSession(String sessionToken, Long userId){
        userSessionRepository.deleteByUserId(userId);

        UserSessionEntity userSessionEntity = new UserSessionEntity();
        userSessionEntity.setSessionToken(sessionToken);
        userSessionEntity.setUserId(userId);

        userSessionRepository.save(userSessionEntity);
    }
}

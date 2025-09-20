package com.week4.prod_ready_features.prod_ready_features.services.Auth;


import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LoginDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LoginResponseDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LogoutResponseDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.SignUpDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Users.UserDTO;
import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.exceptions.UserAlreadyExistsException;
import com.week4.prod_ready_features.prod_ready_features.repositories.UserRepository;
import com.week4.prod_ready_features.prod_ready_features.repositories.UserSessionRepository;
import com.week4.prod_ready_features.prod_ready_features.services.Users.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserSessionService userSessionService;

    @Value("${deploy.env}")
    private String developmentEnvironment;


    public UserDTO signUp(SignUpDTO signUpDTO){

        Optional<UserEntity> userEntity = userRepository.findByEmail(signUpDTO.getEmail());
        if(userEntity.isPresent()){
            throw new UserAlreadyExistsException("User already exists with email: " + signUpDTO.getEmail());
        }

        UserEntity newUser = modelMapper.map(signUpDTO, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        UserEntity savedUser = userRepository.save(newUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }


    public LoginResponseDTO login(LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        String tokenJwt =  jwtService.generateAccessToken(userEntity);

        String refreshTokenJwt = jwtService.generateRefreshToken(userEntity);

        userSessionService.createNewUserSession(refreshTokenJwt, userEntity);

        return new LoginResponseDTO(userEntity.getId(), tokenJwt, refreshTokenJwt);
    }


    public LoginResponseDTO refreshToken(String refreshToken){
        Long userId = jwtService.getUserIdFromToken(refreshToken);

        userSessionService.deleteSession(refreshToken, userId);

        UserEntity userEntity = userService.getUserById(userId);

        String newAccessToken = jwtService.generateAccessToken(userEntity);
        String newRefreshToken = jwtService.generateRefreshToken(userEntity);

        userSessionService.createNewUserSession(newRefreshToken, userEntity);

        return new LoginResponseDTO(userEntity.getId(), newAccessToken, newRefreshToken);

    }

    public String getRefreshTokenFromRequest(HttpServletRequest request){
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token is missing"));
    }

    public LogoutResponseDTO logout(String refreshToken){
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        userSessionService.deleteSession(refreshToken, userId);

        return LogoutResponseDTO.builder().message("User successfully logged out").build();
    }

    public Cookie createNewSecuredRefreshTokenCookie(String refreshToken){
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);

        //requisito para HTTPS e para manter o refresh token seguro para nao ser acessado via JS scripts maliciosos
        // e apenas ficar na camada de servidor
        cookie.setSecure(developmentEnvironment.equals("prod"));

        return cookie;
    }

    public Cookie deleteSecuredRefreshTokenCookie(String refreshToken){
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);

        cookie.setSecure(developmentEnvironment.equals("prod"));
        cookie.setPath("/"); // garantir que seja removido por completo no domínio
        cookie.setMaxAge(0);

        return cookie;
    }




}

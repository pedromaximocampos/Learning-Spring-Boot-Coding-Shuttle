package com.week4.prod_ready_features.prod_ready_features.controllers;


import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LoginDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LoginResponseDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LogoutResponseDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.SignUpDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Users.UserDTO;
import com.week4.prod_ready_features.prod_ready_features.services.Auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.Arrays;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;



    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = authService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        LoginResponseDTO login = authService.login(loginDTO);

        String refreshToken = login.getRefreshToken();

        Cookie cookie = authService.createNewSecuredRefreshTokenCookie(refreshToken);
        response.addCookie(cookie);

        return ResponseEntity.ok(login);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response){
        String oldRefreshToken = authService.getRefreshTokenFromRequest(request);
        LoginResponseDTO loginResponseDTO = authService.refreshToken(oldRefreshToken);

        String newRefreshToken = loginResponseDTO.getRefreshToken();

        // fazendo rotation com o refresh token tambem a cada novo refresh ou a cada novo login.
        Cookie cookie  = authService.createNewSecuredRefreshTokenCookie(newRefreshToken);
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDTO> logout(HttpServletRequest request, HttpServletResponse response){
        String refreshToken = authService.getRefreshTokenFromRequest(request);

        LogoutResponseDTO logoutResponseDTO = authService.logout(refreshToken);

        //Apagando o cookie do refreshToken
        Cookie cookie = authService.deleteSecuredRefreshTokenCookie(refreshToken);
        response.addCookie(cookie);

        return ResponseEntity.ok(logoutResponseDTO);
    }

}

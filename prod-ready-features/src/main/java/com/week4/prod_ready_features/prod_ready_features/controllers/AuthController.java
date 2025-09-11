package com.week4.prod_ready_features.prod_ready_features.controllers;


import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LoginDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LoginResponseDTO;
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

    @Value("${deploy.env}")
    private String developmentEnvironment;

    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = authService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        LoginResponseDTO login = authService.login(loginDTO);

        String refreshToken = login.getRefreshToken();

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);

        //requisito para HTTPS e para manter o refresh token seguro para nao ser acessado via JS scripts maliciosos
        // e apenas ficar na camada de servidor
        cookie.setSecure(developmentEnvironment.equals("prod"));
        response.addCookie(cookie);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(HttpServletRequest request){
        String refreshToken = Arrays.stream(request.getCookies())
                            .filter(cookie -> cookie.getName().equals("refreshToken"))
                            .findFirst()
                            .map(Cookie::getValue)
                            .orElseThrow(() -> new AuthenticationServiceException("Refresh Token is missing"));
        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(loginResponseDTO);
    }

}

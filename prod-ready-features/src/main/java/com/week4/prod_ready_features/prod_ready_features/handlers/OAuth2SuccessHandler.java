package com.week4.prod_ready_features.prod_ready_features.handlers;

import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.exceptions.ResourceNotFoundException;
import com.week4.prod_ready_features.prod_ready_features.services.Auth.JwtService;
import com.week4.prod_ready_features.prod_ready_features.services.Users.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtService jwtService;

    @Value("${deploy.env}")
    private String developmentEnvironment;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws java.io.IOException, ServletException {

        OAuth2AuthenticationToken oAuth2token = (OAuth2AuthenticationToken) authentication;

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) oAuth2token.getPrincipal();


        String email = oAuth2User.getAttribute("email");
        UserEntity user;
        try{
            user = userService.getUserByEmail(email);
        }catch (ResourceNotFoundException ex){
            UserEntity newUser =  UserEntity.builder()
                    .email(email)
                    .name(oAuth2User.getAttribute("name"))
                    .password("oauth2user")
                    .build();
            user = userService.createNewUser(newUser);
        }

        String jwtAccessToken = jwtService.generateAccessToken(user);
        String jwtRefreshToken = jwtService.generateRefreshToken(user);


        Cookie cookie = new Cookie("refreshToken", jwtRefreshToken);
        cookie.setHttpOnly(true);

        //requisito para HTTPS e para manter o refresh token seguro para nao ser acessado via JS scripts maliciosos
        // e apenas ficar na camada de servidor
        cookie.setSecure(developmentEnvironment.equals("prod"));
        response.addCookie(cookie);



        // Redirect to a specific URL or send a custom response
        getRedirectStrategy().sendRedirect(request, response, "http://localhost:9000/home?token=" + jwtAccessToken);
    }
}

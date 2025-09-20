package com.week4.prod_ready_features.prod_ready_features.controllers;

import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LoginDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LogoutResponseDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.SignUpDTO;
import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.entities.UserSessionEntity;
import com.week4.prod_ready_features.prod_ready_features.entities.enums.Role;
import com.week4.prod_ready_features.prod_ready_features.repositories.UserRepository;
import com.week4.prod_ready_features.prod_ready_features.repositories.UserSessionRepository;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTestIT extends AbstractIntegrationTest {

    private SignUpDTO mockedSignUpDTO;

    private UserEntity mockedUserEntity;

    private LoginDTO loginMockedDTO;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        mockedSignUpDTO = SignUpDTO.builder()
                .name("Pedro")
                .email("pedromaximo@gmail.com")
                .roles(Set.of(Role.USER))
                .password("senha123")
                .build();


        mockedUserEntity = modelMapper.map(mockedSignUpDTO, UserEntity.class);
        mockedUserEntity.setPassword(passwordEncoder.encode(mockedSignUpDTO.getPassword()));

        loginMockedDTO = modelMapper.map(mockedUserEntity, LoginDTO.class);
        loginMockedDTO.setPassword("senha123");

        userSessionRepository.deleteAll();
        userRepository.deleteAll();
    }




    @Test
    void testSignUp_whenIsPassedAValidSignUpDTO_thenReturnUserDTO(){

        webTestClient.post()
                .uri("/auth/signUp")
                .bodyValue(mockedSignUpDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(mockedSignUpDTO.getEmail())
                .jsonPath("$.name").isEqualTo(mockedSignUpDTO.getName());

    }

    @Test
    void testSignUp_whenIsPassedAInvalidCreatedSignUpDTO_thenReturnException(){
        UserEntity savedUser = userRepository.save(mockedUserEntity);
        webTestClient.post()
                .uri("/auth/signUp")
                .bodyValue(mockedSignUpDTO)
                .exchange()
                .expectStatus().is4xxClientError();


    }

    @Test
    void testLogin_whenIsPassedAValidLoginDTO_thenReturnLoginResponseDTO(){
        UserEntity savedUser = userRepository.save(mockedUserEntity);

        webTestClient.post()
                .uri("/auth/login")
                .bodyValue(loginMockedDTO)
                .exchange()
                .expectStatus().isOk()
                .expectCookie().exists("refreshToken")
                .expectCookie().value("refreshToken", Assertions::assertNotNull)
                .expectBody()
                .jsonPath("$.userId").isEqualTo(savedUser.getId())
                .jsonPath("$.refreshToken").exists()
                .jsonPath("$.refreshToken").isNotEmpty()
                .jsonPath("$.accessToken").exists()
                .jsonPath("$.accessToken").isNotEmpty();

    }

    @Test
    void testLogin_whenIsPassedAInvalidLoginDTO_thenRaiseException(){
        UserEntity savedUser = userRepository.save(mockedUserEntity);

        loginMockedDTO.setEmail("invalidEmail@gmail.com");

        webTestClient.post()
                .uri("/auth/login")
                .bodyValue(loginMockedDTO)
                .exchange()
                .expectStatus().isUnauthorized();

    }

    @Test
    void testLogout_whenIsPassedAInvalidRefreshToken_thenRaiseException(){

        webTestClient.post()
                .uri("/auth/logout")
                .cookie("refreshToken", "valor_do_refresh_token")
                .exchange()
                .expectStatus().isUnauthorized();

    }

    @Test
    void testLogout_whenIsPassedAValidRefreshToken_thenReturnLogoutResponseDTO(){
        UserEntity savedUser = userRepository.save(mockedUserEntity);

        var response = webTestClient.post()
                .uri("/auth/login")
                .bodyValue(loginMockedDTO)
                .exchange()
                .expectStatus().isOk()
                .expectCookie().exists("refreshToken")
                .expectCookie().value("refreshToken", Assertions::assertNotNull)
                .expectBody()
                .jsonPath("$.userId").isEqualTo(savedUser.getId())
                .jsonPath("$.refreshToken").exists()
                .jsonPath("$.refreshToken").isNotEmpty()
                .jsonPath("$.accessToken").exists()
                .jsonPath("$.accessToken").isNotEmpty()
                .returnResult();

        String refreshToken = response.getResponseCookies()
                .getFirst("refreshToken")
                .getValue();

        webTestClient.post()
                .uri("/auth/logout")
                .cookie("refreshToken", refreshToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LogoutResponseDTO.class);


    }


    @Test
    void testRefresh_whenIsPassedAValidRefreshToken_thenReturnLoginResponseDTO(){
        UserEntity savedUser = userRepository.save(mockedUserEntity);

        var response = webTestClient.post()
                .uri("/auth/login")
                .bodyValue(loginMockedDTO)
                .exchange()
                .expectStatus().isOk()
                .expectCookie().exists("refreshToken")
                .expectCookie().value("refreshToken", Assertions::assertNotNull)
                .expectBody()
                .jsonPath("$.userId").isEqualTo(savedUser.getId())
                .jsonPath("$.refreshToken").exists()
                .jsonPath("$.refreshToken").isNotEmpty()
                .jsonPath("$.accessToken").exists()
                .jsonPath("$.accessToken").isNotEmpty()
                .returnResult();

        String refreshToken = Objects.requireNonNull(response.getResponseCookies()
                        .getFirst("refreshToken"))
                .getValue();

        webTestClient.post()
                .uri("/auth/refresh")
                .cookie("refreshToken", refreshToken)
                .exchange()
                .expectStatus().isOk()
                .expectCookie().exists("refreshToken")
                .expectCookie().value("refreshToken", Assertions::assertNotNull)
                .expectBody()
                .jsonPath("$.userId").isEqualTo(savedUser.getId())
                .jsonPath("$.refreshToken").exists()
                .jsonPath("$.refreshToken").isNotEmpty()
                .jsonPath("$.accessToken").exists()
                .jsonPath("$.accessToken").isNotEmpty();


    }

    @Test
    void testRefresh_whenIsPassedAInvalidRefreshToken_thenRaiseException(){

        webTestClient.post()
                .uri("/auth/refresh")
                .cookie("refreshToken", "valor_do_refresh_token")
                .exchange()
                .expectStatus().isUnauthorized();

    }

}
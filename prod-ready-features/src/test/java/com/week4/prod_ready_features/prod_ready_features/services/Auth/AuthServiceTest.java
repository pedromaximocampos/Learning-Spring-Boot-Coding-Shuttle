package com.week4.prod_ready_features.prod_ready_features.services.Auth;

import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LoginDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LoginResponseDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LogoutResponseDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.SignUpDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Users.UserDTO;
import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.entities.enums.Role;
import com.week4.prod_ready_features.prod_ready_features.repositories.UserRepository;
import com.week4.prod_ready_features.prod_ready_features.services.Users.UserService;
import org.apache.catalina.User;
import org.assertj.core.api.Assert;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {


    private UserEntity mockedUserEntity;

    private SignUpDTO signUpMockedDTO;

    private LoginDTO loginMockedDTO;

    private String accessToken;

    private String refreshToken;

    @Mock
    private UserService userService;

    @Mock
    private UserSessionService userSessionService;

    @Mock
    private Authentication authentication;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Spy // nao precisamos criar um mock de uma biblioteca terceira difernet do nosso repository por isso usamos o spy para usar o "verdadeiro" modelMapper
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void createMockSignUpUser(){
        signUpMockedDTO = SignUpDTO.builder()
                .name("Pedro")
                .email("pedromaximo@gmail.com")
                .roles(Set.of(Role.USER))
                .password("senha123")
                .build();


        mockedUserEntity = modelMapper.map(signUpMockedDTO, UserEntity.class);
        mockedUserEntity.setId(1L);

        loginMockedDTO = modelMapper.map(mockedUserEntity, LoginDTO.class);

        accessToken = "access-token";
        refreshToken = "refresh-token";

    }

    @Test
    void testSignUp_whenIsPassedAValidSignUpDTO_thenReturnUserDTO() {
        // Asign

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        when(userRepository.save(any(UserEntity.class))).thenReturn(mockedUserEntity);

        // Act
        UserDTO newUser = authService.signUp(signUpMockedDTO);

        // Assert

        assertThat(newUser).isNotNull();
        assertThat(newUser.getId()).isEqualTo(mockedUserEntity.getId());
        assertThat(newUser.getEmail()).isEqualTo(signUpMockedDTO.getEmail());
        assertThat(newUser.getName()).isEqualTo(signUpMockedDTO.getName());
        assertThat(newUser.getClass()).isEqualTo(UserDTO.class);

        ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userEntityArgumentCaptor.capture());
        // Usamos o Arguente Captor para ver mais a fundo nossas assertions
        // no caso abaixo estamos vendo se o email que esta sendo "salvo" sera realmente
        // o email  que queremos passar para o repository mockado
        UserEntity capturedUser =  userEntityArgumentCaptor.getValue();
        assertThat(capturedUser.getEmail()).isEqualTo(signUpMockedDTO.getEmail());
    }

    @Test
    void testSignUp_whenIsPassedANonValidSignUpDTO_thenRaiseBadCredentialsException(){
        //Assign
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockedUserEntity));


        //Act & Assert
        assertThatThrownBy(() -> authService.signUp(signUpMockedDTO))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("User already exists with email: " + signUpMockedDTO.getEmail());


        verify(userRepository).findByEmail(signUpMockedDTO.getEmail());

    }

    @Test
    void testLogin_whenIsPassedAValidLoginDTO_thenReturnLoginResponseDTO(){
        //Assign
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(mockedUserEntity);

        when(jwtService.generateAccessToken(mockedUserEntity)).thenReturn(accessToken);
        when(jwtService.generateRefreshToken(mockedUserEntity)).thenReturn(refreshToken);

        //Act

        LoginResponseDTO loginResponse = authService.login(loginMockedDTO);

        // Assert
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getUserId()).isEqualTo(mockedUserEntity.getId());
        assertThat(loginResponse.getAccessToken()).isEqualTo(accessToken);
        assertThat(loginResponse.getRefreshToken()).isEqualTo(refreshToken);
        verify(userSessionService).createNewUserSession(refreshToken, mockedUserEntity);
    }

    @Test
    void testRefreshToken_whenIsPassedAValidRefreshToken_thenReturnLoginResponseDTO(){
        // Assign
        when(jwtService.getUserIdFromToken(anyString())).thenReturn(1L);

        when(userService.getUserById(anyLong())).thenReturn(mockedUserEntity);

        when(jwtService.generateAccessToken(mockedUserEntity)).thenReturn(accessToken);
        when(jwtService.generateRefreshToken(mockedUserEntity)).thenReturn(refreshToken);

        String testValidToken = "test_valid_token";
        // Act
        LoginResponseDTO loginResponseDTO = authService.refreshToken(testValidToken);

        // Assert
        assertThat(loginResponseDTO).isNotNull();
        assertThat(loginResponseDTO.getUserId()).isEqualTo(mockedUserEntity.getId());
        assertThat(loginResponseDTO.getAccessToken()).isEqualTo(accessToken);
        assertThat(loginResponseDTO.getRefreshToken()).isEqualTo(refreshToken);

        verify(userSessionService).deleteSession(testValidToken, 1L);
        verify(userSessionService).createNewUserSession(refreshToken, mockedUserEntity);

    }

    @Test
    void testLogout_whenIsPassedAValidRefreshToken_thenReturnLogoutResponseDTO(){
        // Assert
        when(jwtService.getUserIdFromToken(anyString())).thenReturn(1L);

        //Act

        LogoutResponseDTO logoutResponseDTO = authService.logout("test_valid_refresh_token");

        // Assert

        assertThat(logoutResponseDTO).isNotNull();
        assertThat(logoutResponseDTO.getMessage()).isEqualTo("User successfully logged out");
        verify(userSessionService).deleteSession("test_valid_refresh_token", 1L);
    }

}
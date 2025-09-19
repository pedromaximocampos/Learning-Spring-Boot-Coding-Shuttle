package com.week4.prod_ready_features.prod_ready_features.services.Auth;

import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LoginDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.SignUpDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Users.UserDTO;
import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.entities.enums.Role;
import com.week4.prod_ready_features.prod_ready_features.repositories.UserRepository;
import org.apache.catalina.User;
import org.assertj.core.api.Assert;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {


    private  UserEntity mockedUserEntity;

    private  SignUpDTO signUpMockedDTO;

    @Spy
    private PasswordEncoder passwordEncoder;

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
                .password("sdkaokdoskdoakdoa")
                .build();


        mockedUserEntity = modelMapper.map(signUpMockedDTO, UserEntity.class);
        mockedUserEntity.setId(1L);

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
        assertThat(capturedUser.getEmail()).isEqualTo(signUpMockedDTO);
    }

}
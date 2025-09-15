package com.week4.prod_ready_features.prod_ready_features.repositories;

import com.week4.prod_ready_features.prod_ready_features.TestContainerConfiguration;
import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.entities.enums.Role;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// Importando o testContainer que criaremos com a imagem do docker para simularmos a versao do nossa database
// em producao para termos um teste mais assertivo sem o famoso "na minha máquina funciona".
@Import(TestContainerConfiguration.class)
@DataJpaTest // Usamos o @DataJpaTest para nao usarmos o @SpringBootTest e nao precisar carregar o contexto por completo da aplicacao
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Configuration para usar apenas o DB do test container
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void createTestUser() {
        user = UserEntity.builder()
                .email("pedro@gmail.com")
                .password("pedro1234")
                .name("pedro")
                .roles(Set.of(Role.USER))
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsPresent_ThenReturnUserEntity() {
        // Arrange, Given
        userRepository.save(user);

        // Act, When
        Optional<UserEntity> savedUser = userRepository.findByEmail(user.getEmail());


        // Assert, Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void testFindByEmail_whenEmailsIsNotPresent_ThenReturnNull(){
        // Arrange, Given
        String testUserEmail = "pedrosTeste.123@gmail.com";

        // Act, When
        Optional<UserEntity> savedUser = userRepository.findByEmail(user.getEmail());

        // Assert, Then
        assertThat(savedUser).isEmpty();
    }
}
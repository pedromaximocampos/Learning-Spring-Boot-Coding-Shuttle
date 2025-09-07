package com.week4.prod_ready_features.prod_ready_features.services.Auth;


import com.week4.prod_ready_features.prod_ready_features.dto.Auth.LoginDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Auth.SignUpDTO;
import com.week4.prod_ready_features.prod_ready_features.dto.Users.UserDTO;
import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;


    public UserDTO signUp(SignUpDTO signUpDTO){
        Optional<UserEntity> userEntity = userRepository.findByEmail(signUpDTO.getEmail());
        if(userEntity.isPresent()){
            throw new BadCredentialsException("User already exists with email: " + signUpDTO.getEmail());
        }

        UserEntity newUser = modelMapper.map(signUpDTO, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        UserEntity savedUser = userRepository.save(newUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }


    public String login(LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        return jwtService.generateToken(userEntity);
    }

}

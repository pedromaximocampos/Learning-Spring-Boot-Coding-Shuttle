package com.week4.prod_ready_features.prod_ready_features.services.Users;

import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.exceptions.ResourceNotFoundException;
import com.week4.prod_ready_features.prod_ready_features.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User not found with email: "+ username));
    }

    public UserEntity getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+ id));

    }

    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: "+ email));

    }

    public UserEntity createNewUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

}

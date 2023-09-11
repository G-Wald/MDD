package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.auth.request.LoginRequest;
import com.openclassrooms.mddapi.auth.response.TokenResponse;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User findByUsernameOrEmail(String usernameOrEmail) {
        Optional<User> userFromDb = userRepository.findByUsername(usernameOrEmail);
        if (userFromDb.isEmpty()) {
            userFromDb = userRepository.findByEmail(usernameOrEmail);
        }
        return userFromDb.orElse(null);
    }

    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    public Boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public User save(User user){
        return this.userRepository.save(user);
    }

    public User findById(Integer id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public TokenResponse authenticate(LoginRequest request) {

        var user = findByUsernameOrEmail(request.getUsernameOrEmail());

        if(user == null){
            return TokenResponse.builder().build();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);
        return TokenResponse.builder().token(jwtToken).build();
    }

}

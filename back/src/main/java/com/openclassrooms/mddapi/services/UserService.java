package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.auth.request.LoginRequest;
import com.openclassrooms.mddapi.auth.response.TokenResponse;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

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

        /*authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );*/
        var user = findByUsernameOrEmail(request.getUsernameOrEmail());

        if(user == null){
            return TokenResponse.builder().build();
        }

        var jwtToken = jwtService.generateToken(user);
        return TokenResponse.builder().token(jwtToken).build();
    }

}

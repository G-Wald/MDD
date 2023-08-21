package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.auth.request.LoginRequest;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}

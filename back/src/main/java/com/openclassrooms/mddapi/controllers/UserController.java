package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.auth.request.LoginRequest;
import com.openclassrooms.mddapi.auth.request.RegisterRequest;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
            User user = userService.findByUsernameOrEmail(loginRequest.getUsernameOrEmail());

        if (user == null) {
            logger.info("error");
            return ResponseEntity.status(404).body("Utilisateur non trouvé");
        }
        //if (!userService.passwordEncoder().matches(loginUser.getPassword(), user.getPassword())) {
        //    return ResponseEntity.status(401).body("Mot de passe incorrect");
        //}

        logger.info("connexion réussie");
        return ResponseEntity.ok("Connexion réussie");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            logger.info("error");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already taken!");
        }
        if (userService.existsByUsername(registerRequest.getUsername())) {
            logger.info("error");
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        // Create new user's account
        User user = new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword()
                //passwordEncoder.encode(registerUser.getPassword()),
                );

        userService.save(user);
        logger.info("register ok");
        return ResponseEntity.ok().build();
    }

}

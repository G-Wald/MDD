package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.auth.request.LoginRequest;
import com.openclassrooms.mddapi.auth.request.RegisterRequest;
import com.openclassrooms.mddapi.auth.response.LoginResponse;
import com.openclassrooms.mddapi.auth.response.RegisterResponse;
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
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
            User user = userService.findByUsernameOrEmail(loginRequest.getUsernameOrEmail());

        if (user == null) {
            logger.info("error");
            return ResponseEntity.status(404).body(new LoginResponse("","","Utilisateur non trouvé"));
        }
        //if (!userService.passwordEncoder().matches(loginUser.getPassword(), user.getPassword())) {
        //    return ResponseEntity.status(401).body("Mot de passe incorrect");
        //}

        return ResponseEntity.ok(new LoginResponse(user.getUsername(), user.getEmail(), ""));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest registerRequest) {

        if(!CheckPassword(registerRequest.getPassword())){
            return ResponseEntity
                    .badRequest()
                    .body(new RegisterResponse("\"Password Error: Your password must meet the following criteria to be valid:\n" +
                            "\n" +
                            "    It must contain at least 8 characters.\n" +
                            "    It must include at least one of each of the following:\n" +
                            "        A digit (0-9).\n" +
                            "        A lowercase letter (a-z).\n" +
                            "        An uppercase letter (A-Z).\n" +
                            "        A special character (e.g., !, @, #, $, %, etc.).\""));
        }

        if (userService.existsByEmail(registerRequest.getEmail())) {
            logger.info("error");
            return ResponseEntity
                    .badRequest()
                    .body(new RegisterResponse("Error: Email is already taken!"));
        }
        if (userService.existsByUsername(registerRequest.getUsername())) {
            logger.info("error");
            return ResponseEntity
                    .badRequest()
                    .body(new RegisterResponse("Error: Username is already taken!"));
        }

        // Create new user's account
        User user = new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword()
                //passwordEncoder.encode(registerUser.getPassword()),
                );

        userService.save(user);
        return ResponseEntity.ok(new RegisterResponse(""));
    }

    public Boolean CheckPassword(String password){
        if (password.length() < 8) {
            return false;
        }

        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return false;
        }

        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            return false;
        }

        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }

        return Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]").matcher(password).find();
    }

}

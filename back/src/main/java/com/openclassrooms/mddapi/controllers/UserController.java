package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.auth.request.LoginRequest;
import com.openclassrooms.mddapi.auth.request.RegisterRequest;
import com.openclassrooms.mddapi.auth.request.UsernameRequest;
import com.openclassrooms.mddapi.auth.response.LoginResponse;
import com.openclassrooms.mddapi.auth.response.RegisterResponse;
import com.openclassrooms.mddapi.auth.response.TokenResponse;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class UserController {

    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/profil/{id}")
    public ResponseEntity<LoginResponse> GetProfil(@PathVariable("id") Integer id) {
        User user = userService.findById(id);

        if (user == null) {
            return ResponseEntity.status(404).body(new LoginResponse("", "","","Utilisateur non trouvé"));
        }
        //if (!userService.passwordEncoder().matches(loginUser.getPassword(), user.getPassword())) {
        //    return ResponseEntity.status(401).body("Mot de passe incorrect");
        //}

        return ResponseEntity.ok(new LoginResponse(Integer.toString(user.getId()), user.getUsername(), user.getEmail(), ""));
    }

    @PostMapping("/saveprofil")
    public ResponseEntity<LoginResponse> saveUsername(@RequestBody UsernameRequest usernameRequest) {

        //A modifier apres spring security!!!!!!!!!!!!!!!
        User user = userService.findByUsernameOrEmail(usernameRequest.getUsername());

        if (user == null) {
            logger.info("error");
            return ResponseEntity.status(404).body(new LoginResponse("", "","","Utilisateur non trouvé"));
        }
        //if (!userService.passwordEncoder().matches(loginUser.getPassword(), user.getPassword())) {
        //    return ResponseEntity.status(401).body("Mot de passe incorrect");
        //}

        return ResponseEntity.ok(new LoginResponse(Integer.toString(user.getId()), user.getUsername(), user.getEmail(), ""));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {

        TokenResponse tokenResponse = userService.authenticate(loginRequest);

        if (tokenResponse == null || tokenResponse.getToken() == null || tokenResponse.getToken().equals("")) {
            return ResponseEntity.status(404).body(new TokenResponse("", "Utilisateur non trouvé"));
        }
        return ResponseEntity.ok(tokenResponse);
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
                passwordEncoder.encode(registerRequest.getPassword())
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

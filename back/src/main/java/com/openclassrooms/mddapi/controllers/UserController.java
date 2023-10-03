package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.auth.request.LoginRequest;
import com.openclassrooms.mddapi.auth.request.RegisterRequest;
import com.openclassrooms.mddapi.auth.request.UsernameRequest;
import com.openclassrooms.mddapi.auth.response.*;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public User getUserFromToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            return null;
        }
        return userService.findByUsernameOrEmail(authentication.getName());
    }

    @GetMapping("/profil")
    public ResponseEntity<LoginResponse> GetProfil() {
        User user = this.getUserFromToken();

        if (user == null) {
            return ResponseEntity.status(404).body(new LoginResponse("","","Utilisateur non trouvé"));
        }

        return ResponseEntity.ok(new LoginResponse(user.getUsername(), user.getEmail(), ""));
    }

    @PostMapping("/saveprofil")
    public ResponseEntity<SaveProfilResponse> saveUsername(@RequestBody UsernameRequest usernameRequest) {

        User user = this.getUserFromToken();

        if (user == null) {
            return ResponseEntity.status(404).body(new SaveProfilResponse( "","","","Utilisateur non trouvé"));
        }

        if (!user.getEmail().equals(usernameRequest.getEmail()) && userService.existsByEmail(usernameRequest.getEmail())) {
            logger.info("error");
            return ResponseEntity
                    .badRequest()
                    .body(new SaveProfilResponse("","","","Error: Email is already taken!"));
        }

        if (!user.getUsername().equals(usernameRequest.getUsername()) && userService.existsByUsername(usernameRequest.getUsername())) {
            logger.info("error");
            return ResponseEntity
                    .badRequest()
                    .body(new SaveProfilResponse("","","","Error: Username is already taken!"));
        }

        user.setUsername(usernameRequest.getUsername());
        user.setEmail(usernameRequest.getEmail());
        userService.save(user);

        TokenResponse tokenResponse = userService.authenticate(new LoginRequest( user.getUsername(),user.getPassword()));

        return ResponseEntity.ok(new SaveProfilResponse(user.getUsername(), user.getEmail(),tokenResponse.getToken(), ""));
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

    @GetMapping("/istokenok")
    public ResponseEntity<IsTokenOkResponse> isTokenOk() {
        return ResponseEntity.ok(new IsTokenOkResponse(true));
    }

}

package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.auth.request.userIdRequest;
import com.openclassrooms.mddapi.models.*;
import com.openclassrooms.mddapi.models.responses.ThemeResponse;
import com.openclassrooms.mddapi.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ThemeController {

    public ThemeController(ThemeService themeService,
                           UserService userService,
                           SubscriptionService subscriptionService) {
        this.themeService = themeService;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    private final ThemeService themeService;

    private final UserService userService;

    private final SubscriptionService subscriptionService;

    @GetMapping("/themes")
    public ResponseEntity<?> findThemes() {
        try {
            User user = this.getUserFromToken();

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            List<Theme> themes = this.themeService.findAll();

            if (themes.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            List<Integer> themeIds = this.subscriptionService.findThemesByUserId(user.getId());

            return ResponseEntity.ok().body(themes.stream()
                    .map(theme ->ThemeResponse
                            .builder()
                            .id(theme.getId())
                            .title(theme.getTitle())
                            .description(theme.getDescription())
                            .isSubscribe(themeIds.contains(theme.getId()))
                            .build())
                    .collect(Collectors.toList()));

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public User getUserFromToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            return null;
        }
        return userService.findByUsernameOrEmail(authentication.getName());
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<?> subscribe(@PathVariable("id") Integer id) {
        try {
            User user = this.getUserFromToken();

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            Theme theme = this.themeService.findById(id);

            if (theme == null) {
                return ResponseEntity.notFound().build();
            }

            if(this.subscriptionService.findByUserIdAndByThemeId(user.getId(), theme.getId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User already subscribe");
            }

            this.subscriptionService.save(new Subscription(theme, user));

            return ResponseEntity.ok().build();

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //put
     @DeleteMapping("/unsubscribe/{id}")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") Integer id) {
        try {
            User user = this.getUserFromToken();

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            Theme theme = this.themeService.findById(id);

            if (theme == null) {
                return ResponseEntity.notFound().build();
            }

            if(!this.subscriptionService.findByUserIdAndByThemeId(user.getId(), theme.getId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User not subscribe");
            }

            this.subscriptionService.delete(id, user.getId());
            return ResponseEntity.ok().build();

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

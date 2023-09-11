package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.auth.request.userIdRequest;
import com.openclassrooms.mddapi.models.*;
import com.openclassrooms.mddapi.models.responses.ArticleResponse;
import com.openclassrooms.mddapi.models.responses.ArticlesResponse;
import com.openclassrooms.mddapi.models.responses.ThemeResponse;
import com.openclassrooms.mddapi.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/themes/{id}")
    public ResponseEntity<?> findThemes(@PathVariable("id") Integer id) {
        try {
            User user = this.userService.findById(id);

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
    @PostMapping("/subscribe/{id}")
    public ResponseEntity<?> subscribe(@PathVariable("id") Integer id, @RequestBody userIdRequest userId) {
        try {
            User user = this.userService.findById(userId.getUserId());

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

    //A change en requete delete des que security est mis en place
    //delete n'accepte pas de body donc pas d userId
   /* @PostMapping("/unsubscribe/{id}")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") Integer id) {
        try {
            this.subscriptionService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }*/
    @PostMapping("/unsubscribe/{id}")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") Integer id, @RequestBody userIdRequest userId) {
        try {
            User user = this.userService.findById(userId.getUserId());

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

            this.subscriptionService.delete(id, userId.getUserId());
            return ResponseEntity.ok().build();

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }






}

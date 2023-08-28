package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.auth.request.CommentRequest;
import com.openclassrooms.mddapi.models.*;
import com.openclassrooms.mddapi.models.responses.ArticleResponse;
import com.openclassrooms.mddapi.models.responses.CommentResponse;
import com.openclassrooms.mddapi.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final ArticleService articleService;
    private final UserService userService;
    private final CommentService commentService;

    public CommentController(ArticleService articleService, UserService userService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/newComment/{id}")
    public ResponseEntity<?> subscribe(@PathVariable("id") Integer id, @RequestBody CommentRequest commentRequest) {
        try {
            User user = this.userService.findByUsernameOrEmail(commentRequest.getAuthorUsername());

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            Article article = this.articleService.findById(id);

            if (article == null) {
                return ResponseEntity.notFound().build();
            }

            Comment comment = this.commentService.save(new Comment(commentRequest.getMessage(), article, user));
            return ResponseEntity.ok().body( new CommentResponse(comment.getUser().getUsername(), comment.getMessage()));

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}

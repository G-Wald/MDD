package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.auth.request.LoginRequest;
import com.openclassrooms.mddapi.models.*;
import com.openclassrooms.mddapi.models.responses.ArticleResponse;
import com.openclassrooms.mddapi.models.responses.ArticlesResponse;
import com.openclassrooms.mddapi.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ArticleController {

    public ArticleController(ArticleService articleService, SubscriptionService subscriptionService, ThemeArticleRelationService themeArticleRelationService, CommentService commentService) {
        this.articleService = articleService;
        this.subscriptionService = subscriptionService;
        this.themeArticleRelationService = themeArticleRelationService;
        this.commentService = commentService;
    }

    private final ArticleService articleService;
    private final SubscriptionService subscriptionService;
    private final ThemeArticleRelationService themeArticleRelationService;
    private final CommentService commentService;

    @GetMapping("/article/{id}")
    public ResponseEntity<?> findArticleById(@PathVariable("id") Integer id) {
        try {
            Article article = this.articleService.findById(id);

            if (article == null) {
                return ResponseEntity.notFound().build();
            }

            List<Theme> themes = this.themeArticleRelationService.findThemeByArticleId(article.getId());

            List<Comment> comments = this.commentService.findCommentsByArticleId(article.getId());

            return ResponseEntity.ok().body(ArticleResponse
                    .builder()
                    .title(article.getTitle())
                    .createdAt(article.getCreatedAt())
                    .authorUsername(article.getUser().getUsername())
                    .themes(themes.stream().map(Theme::getTitle).collect(Collectors.toList()))
                    .content(article.getContent())
                    .comment(comments)
                    .build());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<?> findArticlesByUserId(@PathVariable("id") Integer userId) {
        try {
            List<Integer> themeIds = this.subscriptionService.findThemesByUserId(userId);

            if (themeIds.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            List<Article> articles = this.themeArticleRelationService.findArticlesByThemeIds(themeIds);

            if (articles == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(articles.stream()
                    .map(article -> new ArticlesResponse(article.getId(), article.getTitle(), article.getCreatedAt(), article.getUser().getUsername(), article.getContent()))
                    .collect(Collectors.toList()));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

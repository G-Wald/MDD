package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.auth.request.ArticleRequest;
import com.openclassrooms.mddapi.models.*;
import com.openclassrooms.mddapi.models.responses.ArticleResponse;
import com.openclassrooms.mddapi.models.responses.ArticlesResponse;
import com.openclassrooms.mddapi.models.responses.CommentResponse;
import com.openclassrooms.mddapi.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class ArticleController {

    public ArticleController(ArticleService articleService, SubscriptionService subscriptionService, ThemeArticleRelationService themeArticleRelationService, CommentService commentService, UserService userService, ThemeService themeService) {
        this.articleService = articleService;
        this.subscriptionService = subscriptionService;
        this.themeArticleRelationService = themeArticleRelationService;
        this.commentService = commentService;
        this.userService = userService;
        this.themeService = themeService;
    }

    private final ArticleService articleService;
    private final SubscriptionService subscriptionService;
    private final ThemeArticleRelationService themeArticleRelationService;
    private final CommentService commentService;
    private final UserService userService;
    private final ThemeService themeService;

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
                    .id(article.getId())
                    .title(article.getTitle())
                    .createdAt(article.getCreatedAt())
                    .authorUsername(article.getUser().getUsername())
                    .themes(themes.stream().map(Theme::getTitle).collect(Collectors.toList()))
                    .content(article.getContent())
                    .comments(comments.stream().map(comment -> new CommentResponse(comment.getUser().getUsername(), comment.getMessage())).collect(Collectors.toList()))
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
    private static final Logger logger = Logger.getLogger(ArticleController.class.getName());
    @PostMapping("/newarticle/{id}")
    public ResponseEntity<?> createNewArticle(@PathVariable("id") Integer userId, @RequestBody ArticleRequest articleRequest) {
        try {
            User user = this.userService.findById(userId);
            logger.severe("debutr avant user");
            if(user == null){
                return ResponseEntity.notFound().build();
            }
            logger.warning("moimoimoimoimoimoimoimoimoimoimoimoimoimoimoimoi");
            Article dbArticle =this.articleService.save(Article.builder()
                    .user(user)
                    .title(articleRequest.getTitle())
                    .content(articleRequest.getContent())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());
            logger.warning("2");
            for ( int themeId : articleRequest.getThemes()) {
                Theme theme = this.themeService.findById(themeId);
                if(theme == null){
                    return ResponseEntity.badRequest().build();
                }
                this.themeArticleRelationService.save(ThemeArticleRelation.builder()
                        .article(dbArticle)
                        .theme(theme)
                        .build());
            }

            return ResponseEntity.ok().build();

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

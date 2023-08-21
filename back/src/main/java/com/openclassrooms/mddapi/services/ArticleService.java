package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article save(Article article){
        return this.articleRepository.save(article);
    }

    public Article findById(Integer id){
        Optional<Article> article = this.articleRepository.findById(id);
        return article.orElse(null);
    }

    public List<Article> findArticlesByUserId(Integer id){
        return this.articleRepository.findByUserId(id);
    }

}

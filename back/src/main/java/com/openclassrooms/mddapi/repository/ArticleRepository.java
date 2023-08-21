package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Optional<Article> findById(Integer id);

    List<Article> findByUserId(Integer userId);

}

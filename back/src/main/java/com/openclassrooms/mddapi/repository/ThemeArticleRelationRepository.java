package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.ThemeArticleRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeArticleRelationRepository extends JpaRepository<ThemeArticleRelation, Integer> {
    List<ThemeArticleRelation> findByThemeId(Integer themeId);
    List<ThemeArticleRelation> findByArticleId(Integer articleId);
}

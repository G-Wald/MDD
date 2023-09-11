package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.ThemeArticleRelation;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.ThemeArticleRelationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ThemeArticleRelationService {
    private final ThemeArticleRelationRepository themeArticleRelationRepository;

    public ThemeArticleRelationService(ThemeArticleRelationRepository themeArticleRelationRepository) {
        this.themeArticleRelationRepository = themeArticleRelationRepository;
    }

    public List<Article> findArticlesByThemeIds(List<Integer> themeIds){

        List<Article> articles = new ArrayList<Article>();

        for (Integer themeId : themeIds) {
            articles.addAll(this.themeArticleRelationRepository.findByThemeId(themeId).stream().map(ThemeArticleRelation::getArticle).collect(Collectors.toList()));
        }

        Set<Integer> uniqueIds = new HashSet<>();
        List<Article> distinctList = new ArrayList<Article>();

        for (Article article : articles) {
            if (uniqueIds.add(article.getId())) {
                distinctList.add(article);
            }
        }
        return distinctList;
    }

    public List<Theme> findThemeByArticleId(Integer articleId){
        return this.themeArticleRelationRepository.findByArticleId(articleId).stream().map(ThemeArticleRelation::getTheme).collect(Collectors.toList());
    }
    public ThemeArticleRelation save(ThemeArticleRelation themeArticleRelation){
        return this.themeArticleRelationRepository.save(themeArticleRelation);
    }

}

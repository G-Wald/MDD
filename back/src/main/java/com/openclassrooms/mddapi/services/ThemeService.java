package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.ThemeArticleRelation;
import com.openclassrooms.mddapi.repository.ThemeArticleRelationRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }
    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public Theme findById(Integer id) {
        Optional<Theme> theme = this.themeRepository.findById(id);
        return theme.orElse(null);
    }
}

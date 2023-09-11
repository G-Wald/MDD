package com.openclassrooms.mddapi.auth.request;

import lombok.Data;

import java.util.List;

@Data
public class ArticleRequest {
    private List<Integer> themes;
    private String title;
    private String content;
}
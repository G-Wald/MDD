package com.openclassrooms.mddapi.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class ArticlesResponse {
    private Integer id;

    private String title;

    private LocalDateTime createdAt;

    private String authorUsername;

    private String content;
}

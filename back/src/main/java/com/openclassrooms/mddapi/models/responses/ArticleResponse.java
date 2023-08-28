package com.openclassrooms.mddapi.models.responses;

import com.openclassrooms.mddapi.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class ArticleResponse {

    private Integer id;
    private String title;

    private LocalDateTime createdAt;

    private String authorUsername;

    private List<String> themes;

    private String content;

    private List<CommentResponse> comments;
}

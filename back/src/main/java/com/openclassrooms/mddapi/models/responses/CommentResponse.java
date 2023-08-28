package com.openclassrooms.mddapi.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CommentResponse {
    private String authorUsername;
    private String message;
}

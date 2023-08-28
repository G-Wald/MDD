package com.openclassrooms.mddapi.auth.request;

import lombok.Data;


@Data
public class CommentRequest {
    private String authorUsername;
    private String message;
}

package com.openclassrooms.mddapi.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@AllArgsConstructor
@Getter
public class ThemeResponse {
    private int id;

    private String title;

    private String description;

    private Boolean isSubscribe;
}

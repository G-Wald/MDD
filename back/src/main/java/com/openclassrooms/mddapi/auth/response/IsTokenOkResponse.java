package com.openclassrooms.mddapi.auth.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IsTokenOkResponse {
    private Boolean isTokenOk;
}

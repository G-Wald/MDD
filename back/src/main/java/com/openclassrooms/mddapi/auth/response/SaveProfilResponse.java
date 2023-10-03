package com.openclassrooms.mddapi.auth.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveProfilResponse {
    private String username;
    private String email;
    private String token;
    private String error;
}

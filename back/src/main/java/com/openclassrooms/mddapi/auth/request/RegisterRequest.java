package com.openclassrooms.mddapi.auth.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Data
public class RegisterRequest {
    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 3, max = 40)
    private String username;

    @NotBlank
    @Size(min = 6, max = 255)
    private String password;
}

package com.personallibrary.personal_library_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String username;

    @Email(message = "Invalid email")
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

}

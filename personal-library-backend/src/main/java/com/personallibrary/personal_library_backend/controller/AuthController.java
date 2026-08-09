package com.personallibrary.personal_library_backend.controller;

import com.personallibrary.personal_library_backend.dto.*;
import com.personallibrary.personal_library_backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<Object> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ApiResponse.builder()
                .success(true)
                .message("User registered successfully.")
                .data(null)
                .build();

    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ApiResponse.<LoginResponse>builder()
                .success(true)
                .message("Login successful.")
                .data(response)
                .build();

    }

}

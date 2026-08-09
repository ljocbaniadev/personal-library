package com.personallibrary.personal_library_backend.service;

import com.personallibrary.personal_library_backend.dto.LoginRequest;
import com.personallibrary.personal_library_backend.dto.LoginResponse;
import com.personallibrary.personal_library_backend.dto.RegisterRequest;
import com.personallibrary.personal_library_backend.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}

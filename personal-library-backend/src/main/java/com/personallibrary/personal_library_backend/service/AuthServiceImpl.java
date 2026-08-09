package com.personallibrary.personal_library_backend.service;

import com.personallibrary.personal_library_backend.dto.LoginRequest;
import com.personallibrary.personal_library_backend.dto.LoginResponse;
import com.personallibrary.personal_library_backend.dto.RegisterRequest;
import com.personallibrary.personal_library_backend.dto.RegisterResponse;
import com.personallibrary.personal_library_backend.entity.User;
import com.personallibrary.personal_library_backend.exception.InvalidCredentialsException;
import com.personallibrary.personal_library_backend.exception.UserAlreadyExistsException;
import com.personallibrary.personal_library_backend.repository.UserRepository;
import com.personallibrary.personal_library_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return new RegisterResponse("User registered successfully.");
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )

        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid username or password."));

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .username(user.getUsername())
                .token(token)
                .build();

    }

}

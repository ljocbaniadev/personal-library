package com.personallibrary.personal_library_backend.security;

import com.personallibrary.personal_library_backend.entity.User;
import com.personallibrary.personal_library_backend.exception.UserNotFoundException;
import com.personallibrary.personal_library_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticatedUserService {

    private final UserRepository userRepository;
    public User getCurrentUser() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("Authenticated user not found.")
                );
    }
}

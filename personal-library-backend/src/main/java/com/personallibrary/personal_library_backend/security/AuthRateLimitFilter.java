package com.personallibrary.personal_library_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@Component
public class AuthRateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;

    public AuthRateLimitFilter(
            RateLimiterService rateLimiterService
    ) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String method = request.getMethod();
        String uri = request.getRequestURI();

        if (!method.equalsIgnoreCase("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        String ipAddress = getClientIp(request);

        if (uri.equals("/api/auth/login")) {

            boolean allowed = rateLimiterService.isAllowed(
                    "login:" + ipAddress,
                    5,
                    5,
                    Duration.ofMinutes(1)
            );

            if (!allowed) {
                sendRateLimitResponse(
                        response,
                        "Too many login attempts. Please try again later."
                );
                return;
            }
        }

        if (uri.equals("/api/auth/register")) {

            boolean allowed = rateLimiterService.isAllowed(
                    "register:" + ipAddress,
                    3,
                    3,
                    Duration.ofMinutes(10)
            );

            if (!allowed) {
                sendRateLimitResponse(
                        response,
                        "Too many registration attempts. Please try again later."
                );
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendRateLimitResponse(
            HttpServletResponse response,
            String message
    ) throws IOException {

        response.setStatus(429);
        response.setContentType("application/json");

        response.getWriter().write("""
            {
                "status": 429,
                "message": "%s"
            }
            """.formatted(message));
    }

    private String getClientIp(HttpServletRequest request) {

        String forwardedFor = request.getHeader("X-Forwarded-For");

        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }
}
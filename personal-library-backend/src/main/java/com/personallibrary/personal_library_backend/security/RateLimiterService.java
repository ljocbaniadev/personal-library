package com.personallibrary.personal_library_backend.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean isAllowed(
            String key,
            int capacity,
            int refillTokens,
            Duration refillDuration
    ) {

        Bucket bucket = buckets.computeIfAbsent(
                key,
                k -> createBucket(
                        capacity,
                        refillTokens,
                        refillDuration
                )
        );

        return bucket.tryConsume(1);
    }

    private Bucket createBucket(
            int capacity,
            int refillTokens,
            Duration refillDuration
    ) {

        Bandwidth limit = Bandwidth.builder()
                .capacity(capacity)
                .refillIntervally(
                        refillTokens,
                        refillDuration
                )
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
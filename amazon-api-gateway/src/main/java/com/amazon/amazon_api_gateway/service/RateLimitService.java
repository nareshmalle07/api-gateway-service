package com.amazon.amazon_api_gateway.service;

import com.amazon.amazon_api_gateway.config.RateLimitProperties;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitService {

    private final RateLimitProperties rateLimitProperties;

    private final Cache<String, Bucket> buckets;

    public RateLimitService(RateLimitProperties rateLimitProperties) {
        this.rateLimitProperties = rateLimitProperties;

        this.buckets = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterAccess(Duration.ofHours(1))
                .build();
    }

    public Bucket getBucket(String clientId) {

        return buckets.get(
                clientId,
                key -> createNewBucket()
        );
    }

    private Bucket createNewBucket() {

        Bandwidth limit = Bandwidth.classic(
                rateLimitProperties.getCapacity(),
                Refill.greedy(
                        rateLimitProperties.getCapacity(),
                        Duration.ofMinutes(1)
                )
        );

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
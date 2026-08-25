package com.amazon.amazon_api_gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rate-limit")
public class RateLimitProperties {

    private long capacity;

    private long refillTokens;

    private long refillDuration;

    private long cacheMaxSize;

    private long cacheExpiryMinutes;

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public long getRefillTokens() {
        return refillTokens;
    }

    public void setRefillTokens(long refillTokens) {
        this.refillTokens = refillTokens;
    }

    public long getRefillDuration() {
        return refillDuration;
    }

    public void setRefillDuration(long refillDuration) {
        this.refillDuration = refillDuration;
    }

    public long getCacheMaxSize() {
        return cacheMaxSize;
    }

    public void setCacheMaxSize(long cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }

    public long getCacheExpiryMinutes() {
        return cacheExpiryMinutes;
    }

    public void setCacheExpiryMinutes(long cacheExpiryMinutes) {
        this.cacheExpiryMinutes = cacheExpiryMinutes;
    }
}

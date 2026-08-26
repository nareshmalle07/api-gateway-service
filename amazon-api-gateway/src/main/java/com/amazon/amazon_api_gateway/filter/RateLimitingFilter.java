package com.amazon.amazon_api_gateway.filter;

import com.amazon.amazon_api_gateway.service.RateLimitService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(3)
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final Logger log =
            LoggerFactory.getLogger(RateLimitingFilter.class);

    private final RateLimitService rateLimitService;

    public RateLimitingFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        long currentTime = System.currentTimeMillis();
        // Step 1: Identify the client
        String clientId = getClientId(request);

        // Step 2: Get this client's bucket
        Bucket bucket = rateLimitService.getBucket(clientId);

        // Step 3: Try to consume one token
        ConsumptionProbe probe =
                bucket.tryConsumeAndReturnRemaining(1);

        // Step 4: Add rate limit headers
        response.setHeader(
                "X-Rate-Limit-Limit",
                "10"
        );

        response.setHeader(
                "X-Rate-Limit-Remaining",
                String.valueOf(probe.getRemainingTokens())
        );

        // Step 5: Allow the request
        if (probe.isConsumed()) {

            log.info(
                    "Rate limit allowed. Client: {}, Remaining tokens: {}",
                    clientId,
                    probe.getRemainingTokens()
            );

            try {
                filterChain.doFilter(request, response);
            }finally{
                log.info(
                        "RateLimiting filter - Completed Request -> Method: {}, URI: {}, Status: {}, Duration: {} ms",
                        request.getMethod(),
                        request.getRequestURI(),
                        response.getStatus(),
                        (System.currentTimeMillis() - currentTime)
                );
            }


            return;
        }

        // Step 6: Block the request
        log.warn(
                "Rate limit exceeded. Client: {}",
                clientId
        );

        long retryAfterSeconds =
                (long) Math.ceil(
                        probe.getNanosToWaitForRefill()
                                / 1_000_000_000.0
                );

        response.setStatus(
                HttpStatus.TOO_MANY_REQUESTS.value()
        );

        response.setContentType("application/json");

        response.setHeader(
                "Retry-After",
                String.valueOf(retryAfterSeconds)
        );

        response.getWriter().write("""
                {
                  "status": 429,
                  "error": "Too Many Requests",
                  "message": "Rate limit exceeded. Please try again later."
                }
                """);

        return;
    }

    private String getClientId(HttpServletRequest request) {

        String forwardedFor =
                request.getHeader("X-Forwarded-For");

        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor
                    .split(",")[0]
                    .trim();
        }

        return request.getRemoteAddr();
    }
}
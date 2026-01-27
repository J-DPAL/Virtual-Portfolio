package com.portfolio.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    // Store buckets per IP address
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    // Rate limiting rules: 5 requests per minute for messages, 3 for testimonials
    private static final int MESSAGES_RATE_LIMIT = 5;
    private static final int TESTIMONIALS_RATE_LIMIT = 3;
    private static final int DEFAULT_RATE_LIMIT = 100; // For other endpoints

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String ip = getClientIP(request);
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // Only apply rate limiting to POST requests on specific endpoints
        if ("POST".equalsIgnoreCase(method)) {
            int rateLimit = DEFAULT_RATE_LIMIT;
            
            if (requestURI.contains("/api/messages")) {
                rateLimit = MESSAGES_RATE_LIMIT;
            } else if (requestURI.contains("/api/testimonials")) {
                rateLimit = TESTIMONIALS_RATE_LIMIT;
            }

            Bucket bucket = resolveBucket(ip, rateLimit);
            
            if (!bucket.tryConsume(1)) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Too many requests. Please try again later.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private Bucket resolveBucket(String key, int requestsPerMinute) {
        return cache.computeIfAbsent(key, k -> createNewBucket(requestsPerMinute));
    }

    private Bucket createNewBucket(int requestsPerMinute) {
        Bandwidth limit = Bandwidth.classic(
            requestsPerMinute,
            Refill.intervally(requestsPerMinute, Duration.ofMinutes(1))
        );
        return Bucket.builder()
            .addLimit(limit)
            .build();
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}

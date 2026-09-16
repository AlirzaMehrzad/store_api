package com.alirezamehrzad.store.Interceptors;

import com.alirezamehrzad.store.services.RateLimitingService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE) // before Spring Security
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitingService rateLimitingService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String ipAddress = request.getRemoteAddr();

        Bucket tokenBucket = rateLimitingService.resolveBucket(ipAddress);

        if (tokenBucket.tryConsume(1)) {
            // Token acquired, pass to the next filter (Spring Security)
            filterChain.doFilter(request, response);
        } else {
            // Bucket is empty! Reject request before Security even sees it.
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("You have exhausted your API Request Quota.");
        }
    }
}
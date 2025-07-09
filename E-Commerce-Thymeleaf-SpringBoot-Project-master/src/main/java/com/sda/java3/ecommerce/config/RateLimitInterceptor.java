package com.sda.java3.ecommerce.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final Map<String, RequestCounter> requestCounters = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS_PER_MINUTE = 60;
    private static final int WINDOW_SIZE_MS = 60000; // 1 minute

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = getClientIpAddress(request);
        String endpoint = request.getRequestURI();
        String key = clientIp + ":" + endpoint;

        RequestCounter counter = requestCounters.computeIfAbsent(key, k -> new RequestCounter());

        if (counter.isRateLimited()) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded. Please try again later.");
            return false;
        }

        counter.increment();
        return true;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private static class RequestCounter {
        private final AtomicInteger count = new AtomicInteger(0);
        private long windowStart = System.currentTimeMillis();

        public boolean isRateLimited() {
            long now = System.currentTimeMillis();
            
            // Reset counter if window has passed
            if (now - windowStart > WINDOW_SIZE_MS) {
                count.set(0);
                windowStart = now;
                return false;
            }

            return count.get() >= MAX_REQUESTS_PER_MINUTE;
        }

        public void increment() {
            count.incrementAndGet();
        }
    }
} 
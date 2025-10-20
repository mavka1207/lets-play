package com.example.letsplay.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Minimal in-memory rate limiter.
 * IMPORTANT: Only limits POST /auth/login (brute-force protection).
 * It runs after security to avoid masking proper 401/403 responses.
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class RateLimitFilter extends OncePerRequestFilter {

  private static final AntPathMatcher ANT = new AntPathMatcher();

  private static final int LIMIT = 10;        // requests per window
  private static final long WINDOW_MS = 60_000;

  private static final class Window {
    volatile long start;
    final AtomicInteger count = new AtomicInteger(0);
  }

  private final Map<String, Window> buckets = new ConcurrentHashMap<>();

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws ServletException, IOException {

    // Apply rate limit only to POST /auth/login
    if (!shouldLimit(req)) {
      chain.doFilter(req, res);
      return;
    }

    String key = keyFor(req);
    long now = System.currentTimeMillis();

    Window w = buckets.computeIfAbsent(key, k -> {
      Window nw = new Window();
      nw.start = now;
      return nw;
    });

    synchronized (w) {
      if (now - w.start > WINDOW_MS) {
        w.start = now;
        w.count.set(0);
      }
      int cur = w.count.incrementAndGet();
      if (cur > LIMIT) {
        res.setStatus(429);
        res.setContentType("text/plain");
        res.getWriter().write("Too many requests.");
        return;
      }
    }

    chain.doFilter(req, res);
  }

  private boolean shouldLimit(HttpServletRequest req) {
    return "POST".equals(req.getMethod()) && ANT.match("/auth/login", req.getRequestURI());
  }

  private String keyFor(HttpServletRequest req) {
    // simple key: IP + method + URI
    return req.getRemoteAddr() + ":" + req.getMethod() + ":" + req.getRequestURI();
  }
}

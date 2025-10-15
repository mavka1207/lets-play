package com.example.letsplay.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class RateLimitFilter extends OncePerRequestFilter {
private static final int LIMIT = 100; // requests
private static final long WINDOW_MS = 60_000; // per minute


private final Map<String, Counter> buckets = new ConcurrentHashMap<>();


@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
String key = request.getRemoteAddr();
long now = Instant.now().toEpochMilli();
Counter c = buckets.compute(key, (k,v) -> {
if (v == null || now - v.windowStart > WINDOW_MS) return new Counter(1, now);
if (v.count >= LIMIT) return v.blockedCopy();
v.count += 1; return v;
});
if (c.blocked) {
response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
response.getWriter().write("Too many requests. Try again later.");
return;
}
filterChain.doFilter(request, response);
}


private static class Counter {
int count; long windowStart; boolean blocked;
Counter(int count, long ws) { this.count = count; this.windowStart = ws; }
Counter blockedCopy() { Counter n = new Counter(this.count, this.windowStart); n.blocked = true; return n; }
}
}

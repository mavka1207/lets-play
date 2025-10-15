package com.example.letsplay.config;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {
@Bean
public FilterRegistrationBean<RateLimitFilter> rateLimiter(RateLimitFilter filter) {
FilterRegistrationBean<RateLimitFilter> reg = new FilterRegistrationBean<>();
reg.setFilter(filter);
reg.setOrder(0); // Run before Spring Security filter chain
return reg;
}
}

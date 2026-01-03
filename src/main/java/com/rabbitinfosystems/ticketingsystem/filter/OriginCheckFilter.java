package com.rabbitinfosystems.ticketingsystem.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OriginCheckFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(OriginCheckFilter.class);

    private final List<String> allowedOrigins;

    public OriginCheckFilter(@Value("${allowed.origins}") String allowedOriginsProperty) {
        this.allowedOrigins = Arrays.stream(allowedOriginsProperty.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if ("/api/users/register".equals(request.getRequestURI())) {
            String origin = request.getHeader("Origin");
            logger.debug("Origin header: {}", origin);

            if (origin == null || !allowedOrigins.contains(origin)) {
                logger.debug("Blocking request due to mismatched Origin header");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        logger.debug("Allowing request to proceed");
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }
}

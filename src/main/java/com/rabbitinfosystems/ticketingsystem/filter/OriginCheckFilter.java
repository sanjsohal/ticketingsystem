package com.rabbitinfosystems.ticketingsystem.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class OriginCheckFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(OriginCheckFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("/api/users/register".equals(request.getRequestURI())) {
            String origin = request.getHeader("Origin");
            logger.debug("Origin header: {}", origin);

            if (!"https://modern-ticketing-system.vercel.app".equals(origin)) {
                logger.debug("Blocking request due to mismatched Origin header");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        logger.debug("Allowing request to proceed");
        filterChain.doFilter(request, response);
    }
}

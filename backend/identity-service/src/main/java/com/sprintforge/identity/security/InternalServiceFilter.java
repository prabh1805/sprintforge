package com.sprintforge.identity.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class InternalServiceFilter extends OncePerRequestFilter {
    @Value("${internal.key}")
    private static final String INTERNAL_SECRET = "sprintforge-secret";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURL().toString();

        //Protect only internal endpoints
        if(path.startsWith("/internal")){
            String key = request.getHeader("X-Internal-Key");

            if(key == null || !key.equals(INTERNAL_SECRET)){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized internal secret call");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}

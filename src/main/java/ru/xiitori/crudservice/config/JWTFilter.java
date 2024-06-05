package ru.xiitori.crudservice.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.xiitori.crudservice.service.ClientDetailsService;
import ru.xiitori.crudservice.utils.jwt.JWTUtils;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    private final ClientDetailsService clientDetailsService;

    @Autowired
    public JWTFilter(JWTUtils jwtUtils, ClientDetailsService clientDetailsService) {
        this.jwtUtils = jwtUtils;
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");

            if (token.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT token is missing");
            } else {

                try {
                    String username = jwtUtils.validateToken(token);
                    UserDetails userDetails = clientDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT");
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
        }

        filterChain.doFilter(request, response);
    }
}

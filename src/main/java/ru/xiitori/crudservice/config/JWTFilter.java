package ru.xiitori.crudservice.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.xiitori.crudservice.service.ClientDetailsService;
import ru.xiitori.crudservice.utils.jwt.JWTUtils;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Collections;

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
        String authorizationHeader = request.getHeader("Authorization");

        DecodedJWT decodedJWT = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                decodedJWT = jwtUtils.validateToken(token);
            } catch (JWTVerificationException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT!");
            }
        }

        if (decodedJWT != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = clientDetailsService.loadUserByUsername(decodedJWT.getClaim("username").asString());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}

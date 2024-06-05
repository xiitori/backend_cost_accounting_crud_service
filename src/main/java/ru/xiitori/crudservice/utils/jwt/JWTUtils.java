package ru.xiitori.crudservice.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtils {

    @Value("${jwt_secret}")
    private String secret;

    public String createToken(String username) {
        return JWT.create()
                .withSubject("Client details")
                .withIssuer("xiitori")
                .withIssuedAt(new Date())
                .withExpiresAt(ZonedDateTime.now().plusDays(1).toInstant())
                .withClaim("username", username)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateToken(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("xiitori")
                .withSubject("Client details")
                .build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        return decodedJWT.getClaim("username").asString();
    }
}

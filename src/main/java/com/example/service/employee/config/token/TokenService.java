package com.example.service.employee.config.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.service.employee.domain.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    @Value("$api.token.secret")
    private String key;


    public String generatesToken(User principal) {
        try {
            var algorithm = Algorithm.HMAC256(key);
            return JWT.create()
                    .withIssuer("RAFAEL")
                    .withSubject(principal.getLogin())
                    .withExpiresAt(this.expire())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("ERROR IN TOKEN GENERATION", exception);
        }
    }

    private Instant expire()  {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getUser(String token){
        try {
            var algorithm = Algorithm.HMAC256(key);
            return JWT.require(algorithm)
                    .withIssuer("RAFAEL")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("ERROR WHEN VALIDATING TOKEN", exception);
        }
    }

}

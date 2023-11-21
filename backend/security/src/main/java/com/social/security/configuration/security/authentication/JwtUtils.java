package com.social.security.configuration.security.authentication;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.social.security.model.entities.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(UserEntity user) {
    return Jwts.builder()
        .setSubject(String.format("%s,%s", user.getId(), user.getCredentials().getPassword()))
        .setIssuer("ACCEL")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  public boolean validateAccessToken(String token) {
    // try {
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        return true;
/*     } catch (ExpiredJwtException ex) {
        // LOGGER.error("JWT expired", ex.getMessage());
    } catch (IllegalArgumentException ex) {
        // LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
    } catch (MalformedJwtException ex) {
        // LOGGER.error("JWT is invalid", ex);
    } catch (UnsupportedJwtException ex) {
        // LOGGER.error("JWT is not supported", ex);
    } catch (SignatureException ex) {
        // LOGGER.error("Signature validation failed");
    } */
    /* return false; */
  }

  public Claims getClaims(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
  }
    
}

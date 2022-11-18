package com.marindulja.mgmt_sys_demo_2.security;

import com.marindulja.mgmt_sys_demo_2.exception.ASMException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

    @Value("${app.jwt-secret}")
    private String secretKey;

    // generate token
    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();
    }

    // get username from the token
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new ASMException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new ASMException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new ASMException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new ASMException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }
}

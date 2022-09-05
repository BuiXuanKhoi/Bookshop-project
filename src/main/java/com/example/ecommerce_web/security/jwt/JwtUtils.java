package com.example.ecommerce_web.security.jwt;

import com.example.ecommerce_web.exceptions.UnAuthorizationException;
import com.example.ecommerce_web.security.service.UserDetail;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private final String SECRET_KEY;

    private final int EXPIRATION;

    public JwtUtils( @Value("${ecommerce.app.jwtSecret}") final String SECRET_KEY,
                     @Value("${ecommerce.app.jwtExpirationMs}") final int EXPIRATION) {
        this.SECRET_KEY = SECRET_KEY;
        this.EXPIRATION = EXPIRATION;
    }


    public String generateToken(Authentication authentication){

        UserDetail userPrincipal = (UserDetail) authentication.getPrincipal();
        long expirationTime = new Date().getTime() + EXPIRATION;

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String authToken)
    {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        }
        catch (SignatureException signature)
        {
            throw new UnAuthorizationException("Invalid JWT Signature :" + signature.getMessage());
        }
        catch (MalformedJwtException malformException)
        {
            throw new UnAuthorizationException("Invalid Jwt Token :"+ malformException.getMessage());
        }
        catch (ExpiredJwtException expiredException)
        {
            throw new UnAuthorizationException("JWT Token is expired :" + expiredException.getMessage());
        }
        catch (UnsupportedJwtException unsupportException)
        {
            throw new UnAuthorizationException("JWT Token is unsupported: "+ unsupportException.getMessage());
        }
        catch (IllegalArgumentException jwtEmtyException)
        {
            throw new UnAuthorizationException("JWT Token is Empty: "+ jwtEmtyException.getMessage());
        }
        catch (Exception e) {
            throw new UnAuthorizationException("Please Log in");
        }
    }
}

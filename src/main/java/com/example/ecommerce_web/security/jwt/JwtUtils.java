package com.example.ecommerce_web.security.jwt;

import com.example.ecommerce_web.security.service.UserDetail;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtils {

    private  final String SECRET_KEY;
    private final long EXPIRATION;


    public JwtUtils( @Value("${ecommerce.app.jwtSecret}") final String SECRET_KEY,
                     @Value("${ecommerce.app.jwtExpirationMs}") final long EXPIRATION) {
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

    public String generateTokenByUserName(String userName){
        long expirationTime = new Date().getTime() + EXPIRATION;

        return Jwts.builder()
                   .setSubject(userName)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(expirationTime))
                   .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                   .compact();

    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String authToken, HttpServletRequest request)
    {
        try
        {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        }
        catch (SignatureException signature)
        {
            request.setAttribute("invalid signature", signature.getMessage());
        }
        catch (MalformedJwtException malformException)
        {
            request.setAttribute("invalid jwt", malformException.getMessage());
        }
        catch (ExpiredJwtException expiredException)
        {
            request.setAttribute("expired", expiredException.getMessage());
        }
        catch (UnsupportedJwtException unsupportException)
        {
            request.setAttribute("unsupported", unsupportException.getMessage());
        }
        catch (IllegalArgumentException jwtEmptyException)
        {
            request.setAttribute("empty", jwtEmptyException.getMessage());
        }
        catch (Exception e)
        {
            request.setAttribute("undifined", e.getMessage());
        }
        return false;
    }
}

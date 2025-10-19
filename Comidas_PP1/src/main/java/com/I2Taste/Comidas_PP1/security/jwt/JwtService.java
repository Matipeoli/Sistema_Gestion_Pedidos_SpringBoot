package com.I2Taste.Comidas_PP1.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service public class JwtService {      
    // La clave secreta debe ser lo suficientemente larga y compleja.     
    // Es recomendable externalizarla a application.properties.     
    private static final String SECRET_KEY = "Y2xhdmUtc2VjcmV0YS1tdXktbGFyZ2EtcGFyYS1hcGxpY2FjaW9uLXNwcmluZy1ib290";
    
    public String generateToken(String email) {         
        return Jwts.builder()             
            .setSubject(email)             
            .setIssuedAt(new Date(System.currentTimeMillis()))             
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas             
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)             
            .compact();     
        }      
        
    public boolean isTokenValid(String token, UserDetails userDetails) {         
        final String username = extractUsername(token);         
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);     
    }      
    
    public String extractUsername(String token) {         
        return extractClaim(token, Claims::getSubject);     
    }          
    
    private boolean isTokenExpired(String token) {         
        return extractClaim(token, Claims::getExpiration).before(new Date());     
    }          
    
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {         
        final Claims claims = extractAllClaims(token);         
        return claimsResolver.apply(claims);     
    }      
    
    private Claims extractAllClaims(String token) {         
        return Jwts.parserBuilder()             
        .setSigningKey(getSigningKey())             
        .build()             
        .parseClaimsJws(token)             
        .getBody();     
    }      
    
    private Key getSigningKey() {         
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);         
        return Keys.hmacShaKeyFor(keyBytes);     
    } 
} 
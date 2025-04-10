package com.example.UserLibrary.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final static byte[] SECRET = Base64.getDecoder().decode(
            "fq9CvucL0in1dF2J0wHUJKutOJSLDReWiy+MsWw/q7SiBiECGUTdK/qihRLXEfe0m0MKJYrh5a4SWlJuwuKVpppgtYACW8U76a1S2Dr3i82ejIadRIXYnl007MmQne5oQASTbmvm305SIXq9yX+enoQ5OcMnlzQjIuXeoRQ13Sm5j/qCE/UPorIZYnAcyy1Q3y5IMuasQVnvjYWXgXcC1+hd6aMpxqurnM0QxTBTr796S/sOaCLzwhqrw7I/DBRh0Wz/uz1RYZ5pGpeQvOijRc8GApTZ6Gx0vP4ZDbB/D+YKmE2DtMLDaJs7B5vn57UodP7J7X4wSL+WrGwbjZzflg==");

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claim = extractAllClaims(token);
        return claimsResolver.apply(claim);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


}
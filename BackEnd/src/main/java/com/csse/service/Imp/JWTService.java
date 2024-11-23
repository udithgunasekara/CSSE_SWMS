package com.csse.Service.Imp;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

   // private String tokenKey = "";

//    public JWTService() throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
//        tokenKey = Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
//
//    }

    private final String tokenKey = Base64.getEncoder().encodeToString("HereTheSecretCodeWithoutGenerating".getBytes());

    //generating a Token
    public String generateToken(String username) {
//Key returns string, value return object
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder().claims().add(claims).subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 100))
                .and()
                .signWith(getKey())
                .compact(); // compact this will generate a token for you


//        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6InNheXUiLCJpYXQiOjE1MTYyMzkwMjJ9.ZKKEF2_yO50zXqE4Ezu5Ihw_YBSosAuiLs3H3r7ncxg";


    }
//Implementing sercrte key without generating
//    private SecretKey getKey() {
////        KeyGenerator keyGenerator = null;
////        try {
////            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
////        } catch (NoSuchAlgorithmException e) {
////            throw new RuntimeException(e);
////        }
////        tokenKey = Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
//        byte[] keyBytes = Decoders.BASE64.decode(tokenKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//
//    }

    private SecretKey getKey() {
        // Decode the Base64-encoded secret key
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(tokenKey));
    }


    public String extractUsername(String token) {
        //extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {

       final String username= extractUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // at this time
    }

    public boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());


    }
}

package com.demo.library.utils;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    static SecretKey key = Jwts.SIG.HS256.key().build();

    public static String getToken(Map<String, Object> map) {
        return Jwts.builder()
                .claims(map)
                .expiration(new Date(System.currentTimeMillis() + 7200 * 1000))
                .signWith(key)
                .compact();
    }

    public static Map<String, Object> deToken(String token) {
        return new HashMap<>(Jwts.parser()
                .verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload());
    }
}

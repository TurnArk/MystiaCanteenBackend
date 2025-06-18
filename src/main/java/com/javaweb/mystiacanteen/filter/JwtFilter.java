package com.javaweb.mystiacanteen.filter;

import com.javaweb.mystiacanteen.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

public class JwtFilter {
    private static final String KEY = "GUNDAMDD4490721RX782ASWG08GN00ASWG01WINGZERO";
    private static final long EXPIRATION = 15*60*1000;

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(SignatureAlgorithm.HS256,KEY)
                .compact();
    }

    public static Claims getClaimsFromToken(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }catch(MalformedJwtException e){
            System.err.println("Malformed JWT: " + token);  // 可以记录下错误的 Token
            throw new InvalidTokenException("Malformed JWT: " + e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidTokenException("Invalid JWT: " + e.getMessage());
        }
    }

    public static String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public static boolean validateToken(HttpServletRequest request, String username) {
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            System.out.println(token);
            try{
                final String tokenUsername = getUsernameFromToken(token);
                if (username.equals(tokenUsername)) {
                    // 如果用户名一致，进一步检查Token是否过期（可选）
                    if (isTokenExpired(token)) {
                        throw new InvalidTokenException("Token has expired.");
                    }
                    return true; // Token有效且用户名匹配
                }
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private static boolean isTokenExpired(String token) {
        final Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }
}

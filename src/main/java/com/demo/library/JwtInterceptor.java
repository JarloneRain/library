package com.demo.library;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demo.library.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("Authorization").substring(7);
        try {
            Map<String, Object> info = JwtUtils.deToken(token);
            //request.getParameterMap().put("ID", new String[]{info.get("id").toString()});
            request.setAttribute("ID", info.get("id"));
            return true;
        } catch(SignatureException e) {
            map.put("msg", "Error key." + e.getMessage());
        } catch(MalformedJwtException e) {
            map.put("msg", "Error algorithm." + e.getMessage());
        } catch(MissingClaimException e) {
            map.put("msg", "Missing claim data." + e.getMessage());
        } catch(ExpiredJwtException e) {
            map.put("msg", "Expired." + e.getMessage());
        } catch(JwtException e) {
            map.put("msg", "Parsing error." + e.getMessage());
        } catch(Exception e) {
            map.put("msg", "Unknown error." + e.getMessage());
        }
        map.put("state", false);
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}

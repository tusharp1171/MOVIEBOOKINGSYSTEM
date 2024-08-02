package com.example.demo.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
@Component
public class Getuserid  {
	

	    @Autowired
	    private UserRepository userRepository;

	    private final String secretKey = "======================Movie=Spring==========================="; // Replace with your actual secret key

	    public long getUserIdFromToken(String token) {
	        String username = extractUsername(token);
	        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
	        return user.getId();
	    }

	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }

	    private Claims extractAllClaims(String token) {
	        return Jwts.parser()
	                .setSigningKey(secretKey)
	                .parseClaimsJws(token)
	                .getBody();
	    }
	}
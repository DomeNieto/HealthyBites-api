package com.healthybites.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	@Value("${security.jwt.key.private}")
	private String privateKey;
	
	private static final long JWT_EXPIRATION_DATE =  86400000;
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION_DATE);
		
	    String role = authentication.getAuthorities().stream()
	                .findFirst()
	                .map(GrantedAuthority::getAuthority)
	                .orElse("ROLE_USER");
	     
		return Jwts.builder()
				   .subject(username)
				   .claim("role", role)
				   .issuedAt(currentDate)
				   .expiration(expireDate)
				   .signWith(getSignInKey(), Jwts.SIG.HS256)
				   .compact();
	}
	
	private SecretKey getSignInKey() {
		byte[] keyBites = Decoders.BASE64.decode(privateKey);		
		return Keys.hmacShaKeyFor(keyBites);
	}
	
	public String getSubjectFromToken(String token) {
		return extraClaim(token, Claims::getSubject);
	}
	
    public String getRoleFromToken(String token) {
        return extraClaim(token, claims -> claims.get("role", String.class));
    }
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		return extraClaim(token, Claims::getExpiration);
	}
	
	public boolean validateToken(String token) {
		Jwts.parser()
			.verifyWith(getSignInKey())
			.build()
			.parseSignedClaims(token);
		
		return true;
	}
	
	private <T> T extraClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				   .verifyWith(getSignInKey())
				   .build()
				   .parseSignedClaims(token)
				   .getPayload();
	}
}

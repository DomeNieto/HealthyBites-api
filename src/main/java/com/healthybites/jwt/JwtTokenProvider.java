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

/**
 * Utility class for generating and validating JWT tokens.
 * Handles token creation, claims extraction, and signature verification.
 */
@Component
public class JwtTokenProvider {
	
	@Value("${security.jwt.key.private}")
	private String privateKey;
	
	private static final long JWT_EXPIRATION_DATE =  3600000;
	
	/**
     * Generates a JWT token for the authenticated user.
     * Includes username as subject and user role as a claim.
     *
     * @param authentication the authentication object containing user details
     * @return a signed JWT token string
     */
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
	
	/**
     * Retrieves the signing key used to sign and verify JWT tokens.
     * @return the SecretKey derived from the Base64-encoded private key
     */
	private SecretKey getSignInKey() {
		byte[] keyBites = Decoders.BASE64.decode(privateKey);		
		return Keys.hmacShaKeyFor(keyBites);
	}
	
	/**
     * Extracts the subject (username) from the JWT token.
     * @param token the JWT token string
     * @return the username (subject) contained in the token
     */
	public String getSubjectFromToken(String token) {
		return extraClaim(token, Claims::getSubject);
	}
	
	 /**
     * Extracts the user role claim from the JWT token.
     * @param token the JWT token string
     * @return the user role as a string
     */
    public String getRoleFromToken(String token) {
        return extraClaim(token, claims -> claims.get("role", String.class));
    }
	
    /**
     * Validates the JWT token's signature and expiration.
     * @param token the JWT token string
     * @return true if the token is valid, otherwise throws exception
     */
	public boolean validateToken(String token) {
		Jwts.parser()
			.verifyWith(getSignInKey())
			.build()
			.parseSignedClaims(token);
		
		return true;
	}
	
	/**
     * Extracts a specific claim from the JWT token using a claim resolver function.
     * @param token the JWT token string
     * @param claimResolver a function to extract specific claim from Claims object
     * @param <T> the type of the claim to be returned
     * @return the extracted claim value
     */
	private <T> T extraClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	/**
     * Extracts all claims from the JWT token.
     * @param token the JWT token string
     * @return the Claims object extracted from the token
     */
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				   .verifyWith(getSignInKey())
				   .build()
				   .parseSignedClaims(token)
				   .getPayload();
	}
}

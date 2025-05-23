package com.healthybites.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.healthybites.service.userDetails.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT authentication filter that validates JWT tokens in incoming requests.
 * Extends OncePerRequestFilter to ensure the filter executes once per request.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired 
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired 
	UserDetailsServiceImpl userDetailsService;
	
	/**
     * Extracts the JWT token from the Authorization header of the HTTP request.
     * 
     * @param request the HTTP request
     * @return the JWT token string if present and valid format, otherwise null
     */
	private String getTokenFromRequest(HttpServletRequest request) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7, authHeader.length());
		}
		return null;
	}

	 /**
     * Performs the JWT token validation and sets the authentication in the security context.
     * If token is valid, loads user details and sets authentication with the corresponding authorities.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException in case of a servlet error
     * @throws IOException in case of an I/O error
     */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = getTokenFromRequest(request);
		if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			
			String username = jwtTokenProvider.getSubjectFromToken(token);
			String role = jwtTokenProvider.getRoleFromToken(token); 
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			GrantedAuthority authority = new SimpleGrantedAuthority(role);
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
	                    userDetails, null, List.of(authority));
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
		}
		
		filterChain.doFilter(request, response);
		
	}

}

package com.healthybites.configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.healthybites.jwt.JwtAuthenticationFilter;
import com.healthybites.service.userDetails.UserDetailsServiceImpl;

/**
 * Security configuration for the application using Spring Security.
 * Defines endpoint access rules, stateless session policy, JWT integration, and authentication setup.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	/**
     * Configures the HTTP security filter chain.
     *
     * - Disables CSRF protection (appropriate for stateless APIs).
     * - Enables CORS with default settings.
     * - Defines public and role-based access rules for specific API endpoints.
     * - Uses stateless session management (no HTTP sessions).
     * - Adds a JWT authentication filter before the standard username/password filter.
     * - Enables HTTP Basic authentication (primarily for development/testing).
     *
     * @param http the HttpSecurity configuration
     * @return the configured SecurityFilterChain
     */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.cors(Customizer.withDefaults())
				.authorizeHttpRequests(auth -> 
					auth
					// Public documentation access
					.requestMatchers(HttpMethod.GET, "/doc/swagger-ui/**",
														 "/doc/swagger-ui.html",
														 "/v3/api-docs/**"
														 ).permitAll()
					
					// Public authentication and user registration endpoints
						.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
						
					// User-level access
						.requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasRole("USER")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMIN")
						
					// Advice, ingredient, and recipe access
						.requestMatchers(HttpMethod.GET, "/api/v1/advices/**").hasAnyRole("ADMIN", "USER")
						.requestMatchers(HttpMethod.GET, "/api/v1/ingredients/**").hasAnyRole("ADMIN", "USER")
						.requestMatchers(HttpMethod.GET, "/api/v1/recipes/**").hasAnyRole("ADMIN", "USER")
						
					// Admin-only modifications
						.requestMatchers(HttpMethod.POST, "/api/v1/advices/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/v1/advices/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/advices/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/v1/ingredients/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/v1/ingredients/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/ingredients/**").hasRole("ADMIN")
					
					// User-only modifications
						.requestMatchers(HttpMethod.POST, "/api/v1/recipes/**").hasRole("USER")
						.requestMatchers(HttpMethod.PUT, "/api/v1/recipes/**").hasRole("USER")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/recipes/**").hasRole("USER")
						
					// All other requests require authentication
						.anyRequest().authenticated()
						)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.httpBasic(Customizer.withDefaults())
				.build();
	}
	
	 /**
     * Provides the application's authentication manager, required for login/auth processes.
     *
     * @param authenticationConfiguration Spring's authentication configuration
     * @return the AuthenticationManager
     */
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	/**
     * Configures the authentication provider using a custom user details service and password encoder.
     *
     * @param userDetailsService the service used to load user-specific data
     * @return the configured AuthenticationProvider
     */
	@Bean
	AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		
		return provider;
	}
	
	/**
     * Defines the password encoder used to securely hash and verify passwords.
     *
     * @return a BCryptPasswordEncoder instance
     */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
     * Provides the custom JWT authentication filter used to process and validate JWT tokens.
     *
     * @return a JwtAuthenticationFilter instance
     */
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
}


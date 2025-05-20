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

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.cors(Customizer.withDefaults())
				.authorizeHttpRequests(auth -> 
					auth.requestMatchers(HttpMethod.GET, "/doc/swagger-ui/**",
														 "/doc/swagger-ui.html",
														 "/v3/api-docs/**"
														 ).permitAll()
						.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/v1/advices/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/v1/advices/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/advices/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/v1/ingredients/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/v1/ingredients/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/ingredients/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/v1/recipes/**").hasRole("USER")
						.requestMatchers(HttpMethod.PUT, "/api/v1/recipes/**").hasRole("USER")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/recipes/**").hasRole("USER")
						.anyRequest().authenticated()
						)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.httpBasic(Customizer.withDefaults())
				.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		
		return provider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
}


package com.healthybites.service.userDetails;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.healthybites.dtos.auth.AuthLoginRequestDto;
import com.healthybites.dtos.auth.AuthResponseDto;
import com.healthybites.entity.RoleEntity;
import com.healthybites.entity.UserEntity;
import com.healthybites.exception.ResourceNotFoundException;
import com.healthybites.jwt.JwtTokenProvider;
import com.healthybites.repositoy.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	/**
     * Maps a RoleEntity to a collection of GrantedAuthority for Spring Security.
     * @param role the RoleEntity object
     * @return list containing one GrantedAuthority prefixed with "ROLE_"
     */
	public Collection<GrantedAuthority> mapToAuthorities(RoleEntity role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    }
	
	/**
     * Loads a UserDetails object by email.
     * Used by Spring Security for authentication.
     * @param email user email
     * @return UserDetails with email, password, status flags, and authorities
     * @throws ResourceNotFoundException if user with the given email does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email: " + email + " not found"));

        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getIsEnable(),
                userEntity.getAccountNoExpired(),
                userEntity.getAccountNoLocked(),
                userEntity.getCredentialNoExpired(),
                mapToAuthorities(userEntity.getRole())
        );
    }

    /**
     * Authenticates a user by validating the email and password.
     * Throws BadCredentialsException if password does not match.
     * @param email the user email
     * @param password the raw password to verify
     * @return an authenticated UsernamePasswordAuthenticationToken
     * @throws BadCredentialsException if credentials are invalid
     */
    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = this.loadUserByUsername(email);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return new UsernamePasswordAuthenticationToken(
                email,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    /**
     * Performs login authentication and generates a JWT access token on success.
     * Sets the authentication in the SecurityContextHolder.
     * @param authLoginRequest DTO containing email and password for login
     * @return AuthResponseDto containing the JWT access token
     * @throws BadCredentialsException if login fails
     */
    public AuthResponseDto login(AuthLoginRequestDto authLoginRequest) {
        Authentication authentication = this.authenticate(
                authLoginRequest.getEmail(),
                authLoginRequest.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtTokenProvider.generateToken(authentication);

        return new AuthResponseDto(accessToken);
    }
}

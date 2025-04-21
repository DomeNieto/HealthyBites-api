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
	
	public Collection<GrantedAuthority> mapToAuthorities(RoleEntity role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    }

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

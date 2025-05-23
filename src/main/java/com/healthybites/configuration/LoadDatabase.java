package com.healthybites.configuration;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.healthybites.entity.RoleEntity;
import com.healthybites.entity.UserEntity;
import com.healthybites.repositoy.RoleRepository;
import com.healthybites.repositoy.UserRepository;

//@Configuration
public class LoadDatabase {

	@Autowired
	private RoleRepository roleRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository) {
		return arg -> {

			RoleEntity roleAdmin = roleRepository.findByName("ADMIN")
			        .orElseThrow(() -> new RuntimeException("Rol ADMIN no existe en la base de datos"));
	
			
			UserEntity userAdmin = UserEntity.builder()
											.name("Admin")
											.email("administrador@email.com")
											.password(passwordEncoder.encode("admin123"))
											.isEnable(true)
											.accountNoExpired(true)
											.accountNoLocked(true)
											.credentialNoExpired(true)
											.registrationDate(LocalDateTime.now())
											.role(roleAdmin)
						                    .build();
								
		
			userRepository.save(userAdmin);
			
		};
	}
	
}

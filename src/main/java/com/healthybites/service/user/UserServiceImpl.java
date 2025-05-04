package com.healthybites.service.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthybites.dtos.infouser.InfoUserRequestDto;
import com.healthybites.dtos.user.UserEntityRequestDto;
import com.healthybites.dtos.user.UserEntityResponseDto;
import com.healthybites.entity.InfoUserEntity;
import com.healthybites.entity.RoleEntity;
import com.healthybites.entity.UserEntity;
import com.healthybites.exception.ResourceNotFoundException;
import com.healthybites.mappers.infouser.InfoUserMapper;
import com.healthybites.mappers.user.UserMapper;
import com.healthybites.repositoy.RoleRepository;
import com.healthybites.repositoy.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private InfoUserMapper infoUserMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final String USER_NOT_FOUND = "User with id %d not found";
	private static final String USER_NOT_FOUND_MSG = "User with email '%s' not found.";
	private static final String ROLE_NOT_FOUND_MSG = "Role '%s' not found.";
	private static final String DEFAULT_ROLE_NAME = "USER"; 
	
	private UserEntity validateAndGetUser(Long id) {
		return userRepository.findById(id)
							 .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND, id)));
	}
	
	private UserEntity validateAndGetUser(String email) {
		return userRepository.findByEmail(email)
							 .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	}
	
	private RoleEntity getRoleByName(String roleName) {
	    return roleRepository.findByName(roleName)
	            .orElseThrow(() -> new ResourceNotFoundException(String.format(ROLE_NOT_FOUND_MSG, roleName)));
	}
	
	@Override
	public UserEntityResponseDto createUser(UserEntityRequestDto userRequestDto) {
		UserEntity userEntity = userMapper.toUserEntity(userRequestDto);
		userEntity.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
	   
		RoleEntity roleUser = getRoleByName(DEFAULT_ROLE_NAME);
	    userEntity.setRole(roleUser);
	    
		userEntity.setRegistrationDate(LocalDateTime.now());
		
	    if (userEntity.getInfoUser() != null) {
	        userEntity.getInfoUser().setUser(userEntity);
	    }
		UserEntity savedUser = userRepository.save(userEntity);
		return userMapper.toUserResponseDto(savedUser);
	}

	@Override
	public List<UserEntityResponseDto> getAllUsers() {
		return userRepository.findAll().stream()
                .map(userMapper::toUserResponseDto) 
                .collect(Collectors.toList());
	}

	@Override
	public UserEntityResponseDto getUserById(Long id) {
		UserEntity userEntity = validateAndGetUser(id);
		return userMapper.toUserResponseDto(userEntity);
	}

	@Override
	public UserEntityResponseDto updateUser(Long id, UserEntityRequestDto userRequestDto) {
		UserEntity userEntity = validateAndGetUser(id);
		
	    if (userRequestDto.getName() != null && !userRequestDto.getName().trim().isEmpty()) {
	        userEntity.setName(userRequestDto.getName());
	    }
	  
		if (userRequestDto.getEmail() != null && !userEntity.getEmail().equalsIgnoreCase(userRequestDto.getEmail())) {
            userEntity.setEmail(userRequestDto.getEmail());
		}
		
		if(userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty()) {
			userEntity.setPassword(passwordEncoder.encode(userRequestDto.getPassword())); 
		}

		 InfoUserRequestDto infoDto = userRequestDto.getInfoUser();
	     InfoUserEntity existingInfo = userEntity.getInfoUser(); 

	     if (infoDto != null) {
	         if (existingInfo == null) {
	             existingInfo = infoUserMapper.toInfoUserEntity(infoDto);
	             userEntity.setInfoUser(existingInfo);
	             existingInfo.setUser(userEntity);    
	         } else {
	             if (infoDto.getHeight() != null) {
	                 existingInfo.setHeight(infoDto.getHeight());
	             }
	             if (infoDto.getWeight() != null) {
	                 existingInfo.setWeight(infoDto.getWeight());
	             }
	             if (infoDto.getActivityLevel() != null && !infoDto.getActivityLevel().isEmpty()) {
	                 existingInfo.setActivityLevel(infoDto.getActivityLevel());
	             }
	         }
	     }
	    
	        UserEntity updatedUser = userRepository.save(userEntity);
	        return userMapper.toUserResponseDto(updatedUser);
	    }

	@Override
	public void deleteUser(Long id) {
		UserEntity userEntity = validateAndGetUser(id);
		userRepository.delete(userEntity);
	}

	@Override
	public UserEntityResponseDto findUserByEmail(String email) {
		  UserEntity userEntity = validateAndGetUser(email);
		  return userMapper.toUserResponseDto(userEntity);
	}

}

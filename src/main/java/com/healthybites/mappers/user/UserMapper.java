package com.healthybites.mappers.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.healthybites.dtos.user.UserEntityRequestDto;
import com.healthybites.dtos.user.UserEntityResponseDto;
import com.healthybites.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "accountNoExpired", constant = "true")
	@Mapping(target = "accountNoLocked", constant = "true")
	@Mapping(target = "credentialNoExpired", constant = "true")
	@Mapping(target = "isEnable", constant = "true")
	@Mapping(target = "registrationDate", ignore = true)
	UserEntity toUserEntity(UserEntityRequestDto userRequestDto);
	
	UserEntityResponseDto toUserResponseDto(UserEntity userEntity); 
}


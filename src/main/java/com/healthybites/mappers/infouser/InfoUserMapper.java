package com.healthybites.mappers.infouser;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.healthybites.dtos.infouser.InfoUserRequestDto;
import com.healthybites.dtos.infouser.InfoUserResponseDto;
import com.healthybites.entity.InfoUserEntity;

@Mapper(componentModel = "spring")
public interface InfoUserMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	InfoUserEntity toInfoUserEntity(InfoUserRequestDto infoUserRequestDto);
	
	InfoUserResponseDto toInfoUserResponse(InfoUserEntity infoUser);
}

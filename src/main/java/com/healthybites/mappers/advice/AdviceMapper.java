package com.healthybites.mappers.advice;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.healthybites.dtos.advice.AdviceRequestDto;
import com.healthybites.dtos.advice.AdviceResponseDto;
import com.healthybites.entity.AdviceEntity;

@Mapper(componentModel = "spring")
public interface AdviceMapper {

	// dto-> entity
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	AdviceEntity toAdvice(AdviceRequestDto adviceResquestDto);
	
	//entity to dto
	AdviceResponseDto toAdviceResponseDto(AdviceEntity advice);
}

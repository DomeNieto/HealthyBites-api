package com.healthybites.mappers.advice;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.healthybites.dtos.advice.AdviceRequestDto;
import com.healthybites.dtos.advice.AdviceResponseDto;
import com.healthybites.entity.AdviceEntity;

@Mapper(componentModel = "spring")
public interface AdviceMapper {

	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	/*
	 * Converts an AdviceRequestDto to an AdviceEntity.
	 */
	AdviceEntity toAdvice(AdviceRequestDto adviceResquestDto);
	
	/*
	 * * Converts an AdviceEntity to an AdviceResponseDto.
	 */
	AdviceResponseDto toAdviceResponseDto(AdviceEntity advice);
}

package com.healthybites.mappers.advice;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.healthybites.dtos.advice.AdviceRequestDto;
import com.healthybites.dtos.advice.AdviceResponseDto;
import com.healthybites.entity.AdviceEntity;

@Mapper(componentModel = "spring")
public interface AdviceMapper {

	
	/**
	 * Converts an AdviceRequestDto to an AdviceEntity.
	 * Ignores the id and creationDate fields during the conversion.
	 * @param adviceResquestDto the AdviceRequestDto to convert
	 * @return the converted AdviceEntity
	 */
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	AdviceEntity toAdvice(AdviceRequestDto adviceResquestDto);
	
	/**
	 * Converts an AdviceEntity to an AdviceResponseDto.
	 * @param advice the AdviceEntity to convert
	 * @return 	the converted AdviceResponseDto
	 */
	AdviceResponseDto toAdviceResponseDto(AdviceEntity advice);
}

package com.healthybites.mappers.ingredient;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.healthybites.dtos.ingredient.IngredientRequestDto;
import com.healthybites.dtos.ingredient.IngredientResponseDto;
import com.healthybites.entity.IngredientEntity;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    // dto -> entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recipeIngredients", ignore = true) 
    /*
     * Converts an IngredientRequestDto to an IngredientEntity.
     * Ignores the id and recipeIngredients fields during the conversion.
     * 
     * @param dto the IngredientRequestDto to convert
     * @return the converted IngredientEntity
    */
    IngredientEntity toIngredient(IngredientRequestDto dto);

    // entity -> dto
    @Mapping(target = "quantity", constant = "0") 
    @Mapping(target = "quantityCalories", expression = "java(entity.getQuantityCalories())") 
    /*
     * Converts an IngredientEntity to an IngredientResponseDto.
     * Ignores the quantity field and sets it to 0 during the conversion.
     * 
     * @param entity the IngredientEntity to convert
     * @return the converted IngredientResponseDto
    */
    IngredientResponseDto toIngredientResponseDto(IngredientEntity entity);
}

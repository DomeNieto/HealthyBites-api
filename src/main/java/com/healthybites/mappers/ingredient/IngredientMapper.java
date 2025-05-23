package com.healthybites.mappers.ingredient;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.healthybites.dtos.ingredient.IngredientRequestDto;
import com.healthybites.dtos.ingredient.IngredientResponseDto;
import com.healthybites.entity.IngredientEntity;

@Mapper(componentModel = "spring")
public interface IngredientMapper {


    /*
     * Converts an IngredientRequestDto to an IngredientEntity.
     * Ignores the id and recipeIngredients fields during the conversion.
     * 
     * @param dto the IngredientRequestDto to convert
     * @return the converted IngredientEntity
    */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recipeIngredients", ignore = true) 
    IngredientEntity toIngredient(IngredientRequestDto dto);

    /*
     * Converts an IngredientEntity to an IngredientResponseDto.
     * Ignores the recipeIngredients field during the conversion.
     * 
     * @param entity the IngredientEntity to convert
     * @return the converted IngredientResponseDto
    */
    @Mapping(target = "quantity", constant = "0") 
    @Mapping(target = "quantityCalories", expression = "java(entity.getQuantityCalories())") 
    IngredientResponseDto toIngredientResponseDto(IngredientEntity entity);
}

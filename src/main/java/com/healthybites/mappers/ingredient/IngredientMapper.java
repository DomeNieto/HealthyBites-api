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
    IngredientEntity toIngredient(IngredientRequestDto dto);

    // entity -> dto
    @Mapping(target = "quantity", constant = "0") 
    @Mapping(target = "quantityCalories", expression = "java(entity.getQuantityCalories())") 
    IngredientResponseDto toIngredientResponseDto(IngredientEntity entity);
}

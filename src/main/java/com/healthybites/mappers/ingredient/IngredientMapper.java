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
    @Mapping(source = "quantityCalories", target = "quantity_calories")
    @Mapping(target = "recipeIngredients", ignore = true) 
    IngredientEntity toIngredient(IngredientRequestDto dto);

    // entity -> dto
    @Mapping(source = "quantity_calories", target = "quantityCalories")
    IngredientResponseDto toIngredientResponseDto(IngredientEntity entity);
}

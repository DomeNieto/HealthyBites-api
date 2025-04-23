package com.healthybites.mappers.recipeingredient;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.healthybites.dtos.recipeingredient.RecipeIngredientResponseDto;
import com.healthybites.entity.RecipeIngredientEntity;

@Mapper(componentModel = "spring")
public interface RecipeIngredientMapper {

    @Mapping(source = "ingredient.id", target = "ingredientId")
    @Mapping(source = "ingredient.name", target = "name")
    @Mapping(source = "quantity", target = "quantity")
    RecipeIngredientResponseDto toDto(RecipeIngredientEntity entity);

    List<RecipeIngredientResponseDto> toDtoList(Set<RecipeIngredientEntity> entities);
}

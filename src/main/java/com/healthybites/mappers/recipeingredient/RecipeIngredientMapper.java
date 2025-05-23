package com.healthybites.mappers.recipeingredient;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.healthybites.dtos.recipeingredient.RecipeIngredientResponseDto;
import com.healthybites.entity.RecipeIngredientEntity;

@Mapper(componentModel = "spring")
public interface RecipeIngredientMapper {

    /**
     * Converts a RecipeIngredientEntity to a RecipeIngredientResponseDto.
     * 
     * @param entity the RecipeIngredientEntity to convert
     * @return the converted RecipeIngredientResponseDto
     */
    @Mapping(source = "ingredient.id", target = "ingredientId")
    @Mapping(source = "ingredient.name", target = "name")
    @Mapping(source = "quantity", target = "quantity")
    RecipeIngredientResponseDto toDto(RecipeIngredientEntity entity);

    /**
     * Converts a set of RecipeIngredientEntity to a list of RecipeIngredientResponseDto
     * @param entities the set of RecipeIngredientEntity to convert
     * @return the converted list of RecipeIngredientResponseDto
     */
    List<RecipeIngredientResponseDto> toDtoList(Set<RecipeIngredientEntity> entities);
}

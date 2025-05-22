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
    /**
     * Converts a RecipeIngredientEntity to a RecipeIngredientResponseDto.
     * 
     * @param entity the RecipeIngredientEntity to convert
     * @return the converted RecipeIngredientResponseDto
     */
    RecipeIngredientResponseDto toDto(RecipeIngredientEntity entity);

    /*  
     * Converts a set of RecipeIngredientEntity to a list of RecipeIngredientResponseDto.
     */
    List<RecipeIngredientResponseDto> toDtoList(Set<RecipeIngredientEntity> entities);
}

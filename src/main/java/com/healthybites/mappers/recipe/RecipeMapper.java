package com.healthybites.mappers.recipe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.healthybites.dtos.recipe.RecipeRequestDto;
import com.healthybites.dtos.recipe.RecipeResponseDto;
import com.healthybites.entity.RecipeEntity;
import com.healthybites.mappers.recipeingredient.RecipeIngredientMapper;

@Mapper(componentModel = "spring", uses = {RecipeIngredientMapper.class})
public interface RecipeMapper {

	@Mapping(target = "id", ignore = true)
    @Mapping(target = "recipeIngredients", ignore = true)
    @Mapping(target = "user", ignore = true)  
    /*
     * Converts a RecipeRequestDto to a RecipeEntity.
     * Ignores the id, recipeIngredients, and user fields during the conversion.
     * 
     * @param dto the RecipeRequestDto to convert
     * @return the converted RecipeEntity
    */
    RecipeEntity toRecipe(RecipeRequestDto dto);

    @Mapping(target = "ingredients", ignore = true)
    /*
     * Converts a RecipeEntity to a RecipeResponseDto.
     * Ignores the ingredients field during the conversion.
     * 
     * @param entity the RecipeEntity to convert
     * @return the converted RecipeResponseDto
    */
    RecipeResponseDto toRecipeResponseDto(RecipeEntity entity);
}


package com.healthybites.mappers.recipe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.healthybites.dtos.recipe.RecipeRequestDto;
import com.healthybites.dtos.recipe.RecipeResponseDto;
import com.healthybites.entity.RecipeEntity;
import com.healthybites.mappers.recipeingredient.RecipeIngredientMapper;

@Mapper(componentModel = "spring", uses = {RecipeIngredientMapper.class})
public interface RecipeMapper {

    /*
     * Converts a RecipeRequestDto to a RecipeEntity.
     * Ignores the id, recipeIngredients, and user fields during the conversion.
     * 
     * @param dto the RecipeRequestDto to convert
     * @return the converted RecipeEntity
    */
	@Mapping(target = "id", ignore = true)
    @Mapping(target = "recipeIngredients", ignore = true)
    @Mapping(target = "user", ignore = true)  
    RecipeEntity toRecipe(RecipeRequestDto dto);

    /*
     * Converts a RecipeEntity to a RecipeResponseDto.
     * Ignores the ingredients field during the conversion.
     * 
     * @param entity the RecipeEntity to convert
     * @return the converted RecipeResponseDto
    */
    @Mapping(target = "ingredients", ignore = true)
    RecipeResponseDto toRecipeResponseDto(RecipeEntity entity);
}


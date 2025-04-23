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
    RecipeEntity toRecipe(RecipeRequestDto dto);

    @Mapping(target = "ingredients", source = "recipeIngredients")
    RecipeResponseDto toRecipeResponseDto(RecipeEntity entity);
}

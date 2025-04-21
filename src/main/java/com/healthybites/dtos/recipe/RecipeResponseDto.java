package com.healthybites.dtos.recipe;

import java.util.List;

import com.healthybites.dtos.recipeingredient.RecipeIngredientResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseDto {

    private String name;
    private String preparation;
    private List<RecipeIngredientResponseDto> ingredients;
}

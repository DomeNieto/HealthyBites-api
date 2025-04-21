package com.healthybites.dtos.recipeingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientResponseDto {
    private Long ingredientId;
    private String name;
    private float quantity;
}
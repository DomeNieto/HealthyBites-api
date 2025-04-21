package com.healthybites.dtos.recipeingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientRequestDto {
    private Long ingredientId;
    private float quantity;
}

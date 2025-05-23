package com.healthybites.dtos.ingredient;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

/**
 * IngredientResponseDto
 * The response body containing the details of the ingredient.
 */
public class IngredientResponseDto {
	private long id;
    private String name;
    private float quantity;
    private float quantityCalories;
    private boolean active; 
    private LocalDateTime creationDate;
}
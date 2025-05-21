package com.healthybites.dtos.ingredient;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDto {
	private long id;
    private String name;
    private float quantity;
    private float quantityCalories;
    private boolean active; 
    private LocalDateTime creationDate;
}
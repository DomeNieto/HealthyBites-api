package com.healthybites.dtos.ingredient;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDto {

    private String name;
    private float quantityCalories;
    private LocalDateTime creationDate;
}
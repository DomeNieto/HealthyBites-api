package com.healthybites.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long recipe;
    private Long ingredient;
}   
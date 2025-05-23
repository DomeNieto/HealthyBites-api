package com.healthybites.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RecipeIngredientEntity
 * Represents a many-to-many relationship between recipes and ingredients.
 * Contains fields for recipe, ingredient, and quantity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe_ingredient")
@IdClass(RecipeIngredientId.class)
public class RecipeIngredientEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeEntity recipe;

    @Id
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private IngredientEntity ingredient;

    @Column(nullable = false)
    private float quantity;
}

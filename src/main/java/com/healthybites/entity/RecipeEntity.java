package com.healthybites.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe")
public class RecipeEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 80)
	private String name;
	
	@Column(nullable = false, length = 500)
	private String preparation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
    private UserEntity user;
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<RecipeIngredientEntity> recipeIngredients = new HashSet<>();

	public void addIngredient(IngredientEntity ingredient, float quantity) {
	    RecipeIngredientEntity link = new RecipeIngredientEntity(this, ingredient, quantity);
	    recipeIngredients.add(link);
	    ingredient.getRecipeIngredients().add(link);
	}
	
	public void removeIngredient(IngredientEntity ingredient, float quantity) {
	    RecipeIngredientEntity link = new RecipeIngredientEntity(this, ingredient, quantity);
	    recipeIngredients.remove(link);
	    ingredient.getRecipeIngredients().remove(link);
	}
	
}

package com.healthybites.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredient")
public class IngredientEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 60)
	private String name;
	
	@Column(nullable = false)
	private float quantityCalories;
	
	@Column(nullable = false)
	private LocalDateTime creationDate;

	@Column(nullable = false)
    private boolean active;
	
	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<RecipeIngredientEntity> recipeIngredients = new HashSet<>();

	
}

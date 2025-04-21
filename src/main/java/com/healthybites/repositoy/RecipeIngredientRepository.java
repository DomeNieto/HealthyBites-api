package com.healthybites.repositoy;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthybites.entity.RecipeIngredientEntity;
import com.healthybites.entity.RecipeIngredientId;


@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, RecipeIngredientId> {
    // 1) Todas las associations de ingredientes de una receta concreta
    List<RecipeIngredientEntity> findByRecipeId(Long recipeId);

    // 2) Todas las associations de recetas que usan un ingrediente concreto
    List<RecipeIngredientEntity> findByIngredientId(Long ingredientId);

    // 3) Borrar todas las associations de una receta si quieres
    void deleteByRecipeId(Long recipeId);
}

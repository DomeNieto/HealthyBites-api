package com.healthybites.repositoy;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.healthybites.entity.RecipeIngredientEntity;
import com.healthybites.entity.RecipeIngredientId;

import jakarta.transaction.Transactional;


@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, RecipeIngredientId> {
    List<RecipeIngredientEntity> findByRecipeId(Long recipeId);

    List<RecipeIngredientEntity> findByIngredientId(Long ingredientId);

    @Modifying
    @Transactional
    @Query("delete from RecipeIngredientEntity ri where ri.recipe.id = :recipeId")
    void deleteByRecipeId(@Param("recipeId") Long recipeId);

    @Modifying
    @Transactional
    @Query("delete from RecipeIngredientEntity ri where ri.ingredient.id = :ingredientId")
    void deleteByIngredientId(@Param("ingredientId") Long ingredientId);

    @Modifying
    @Transactional
    @Query("delete from RecipeIngredientEntity ri where ri.recipe.id = :recipeId and ri.ingredient.id = :ingredientId ")
    void deleteByRecipeIdAndIngredientId(@Param("recipeId") Long recipeId, @Param("ingredientId") Long ingredientId);
}

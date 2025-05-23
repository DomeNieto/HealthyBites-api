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
    /**
     * Finds a list of RecipeIngredientEntity by recipe ID.
     * 
     * @param recipeId the ID of the recipe
     * @return a list of RecipeIngredientEntity
     */
    List<RecipeIngredientEntity> findByRecipeId(Long recipeId);

    /**
     * Finds a list of RecipeIngredientEntity by ingredient ID.
     * 
     * @param ingredientId the ID of the ingredient
     * @return a list of RecipeIngredientEntity
     */
    List<RecipeIngredientEntity> findByIngredientId(Long ingredientId);

    /**
     * Deletes a RecipeIngredientEntity by recipe ID.
     * 
     * @param recipeId the ID of the recipe
     */
    @Modifying
    @Transactional
    @Query("delete from RecipeIngredientEntity ri where ri.recipe.id = :recipeId")
    void deleteByRecipeId(@Param("recipeId") Long recipeId);

    /**
     * Deletes a RecipeIngredientEntity by ingredient ID.
     * 
     * @param ingredientId the ID of the ingredient
     */
    @Modifying
    @Transactional
    @Query("delete from RecipeIngredientEntity ri where ri.ingredient.id = :ingredientId")
    void deleteByIngredientId(@Param("ingredientId") Long ingredientId);

    /**
     * Deletes a RecipeIngredientEntity by recipe ID and ingredient ID.
     * 
     * @param recipeId the ID of the recipe
     * @param ingredientId the ID of the ingredient
     */
    @Modifying
    @Transactional
    @Query("delete from RecipeIngredientEntity ri where ri.recipe.id = :recipeId and ri.ingredient.id = :ingredientId ")
    void deleteByRecipeIdAndIngredientId(@Param("recipeId") Long recipeId, @Param("ingredientId") Long ingredientId);
}

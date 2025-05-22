package com.healthybites.repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthybites.entity.RecipeEntity;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
	/**
	 * Finds a list of RecipeEntity by user ID.
	 * 
	 * @param userId the ID of the user
	 * @return a list of RecipeEntity
	 */
	List<RecipeEntity> findByUserId(Long userId);

	/**
	 * Finds a RecipeEntity by name.
	 * 
	 * @param name the name of the recipe
	 * @return a RecipeEntity
	 */
	boolean existsByName(String name);
	
}

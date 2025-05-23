package com.healthybites.repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthybites.entity.IngredientEntity;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
	/**
	 * Finds a list of IngredientEntity by active status.
	 * 
	 * @return a list of IngredientEntity
	 */
	List<IngredientEntity> findByActiveTrue();

}

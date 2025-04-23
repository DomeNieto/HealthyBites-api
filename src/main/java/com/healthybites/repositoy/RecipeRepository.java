package com.healthybites.repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthybites.entity.RecipeEntity;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
	List<RecipeEntity> findByUserId(Long userId);
	boolean existsByName(String name);
	
}

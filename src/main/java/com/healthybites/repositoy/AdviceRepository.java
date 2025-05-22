package com.healthybites.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthybites.entity.AdviceEntity;
@Repository
/**
 * Repository interface for managing AdviceEntity objects.
 * This interface extends JpaRepository to provide CRUD operations.
 */
public interface AdviceRepository extends JpaRepository<AdviceEntity, Long> {

}

package com.healthybites.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthybites.entity.AdviceEntity;
@Repository
public interface AdviceRepository extends JpaRepository<AdviceEntity, Long> {

}

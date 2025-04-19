package com.healthybites.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthybites.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}

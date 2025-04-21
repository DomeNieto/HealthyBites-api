package com.healthybites.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthybites.entity.InfoUserEntity;

public interface InfoUserRepository extends JpaRepository<InfoUserEntity, Long> {

}

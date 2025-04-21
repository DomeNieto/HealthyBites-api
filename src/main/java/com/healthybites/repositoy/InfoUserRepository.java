package com.healthybites.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.healthybites.entity.InfoUserEntity;

@Repository
public interface InfoUserRepository extends JpaRepository<InfoUserEntity, Long> {

}

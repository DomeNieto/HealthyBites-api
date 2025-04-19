package com.healthybites.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import com.healthybites.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}

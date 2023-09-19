package org.example.Lab2_BLPS.service.authorization.service.repository;

import org.apache.catalina.User;
import org.example.Lab2_BLPS.service.authorization.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
}

package com.badomega.hims.repositories;

import com.badomega.hims.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(@Param("username") String username);
}

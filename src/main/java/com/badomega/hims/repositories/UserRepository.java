package com.badomega.hims.repositories;

import com.badomega.hims.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by melany on 11/12/2016.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}

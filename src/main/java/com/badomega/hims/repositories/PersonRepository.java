package com.badomega.hims.repositories;

import com.badomega.hims.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}

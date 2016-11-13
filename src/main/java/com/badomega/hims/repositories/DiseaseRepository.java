package com.badomega.hims.repositories;

import com.badomega.hims.entities.Disease;
import org.springframework.data.repository.CrudRepository;

public interface DiseaseRepository extends CrudRepository<Disease, Integer> {
}

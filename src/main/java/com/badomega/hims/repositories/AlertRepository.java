package com.badomega.hims.repositories;

import com.badomega.hims.entities.Alert;
import org.springframework.data.repository.CrudRepository;

public interface AlertRepository extends CrudRepository<Alert, Integer> {
}

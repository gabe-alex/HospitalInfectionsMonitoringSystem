package com.badomega.hims.repositories;

import com.badomega.hims.entities.Interaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface InteractionRepository extends CrudRepository<Interaction, Integer> {
    @Query("select i " +
            "from Interaction i " +
            "join i.self s " +
            "left join i.targetPerson tp " +
            "left join i.targetBeacon tb " +
            "where (tp is not null or tb is not null)")
    Set<Interaction> getValid();
}

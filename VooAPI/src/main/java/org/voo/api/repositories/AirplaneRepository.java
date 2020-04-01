package org.voo.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.voo.api.models.Airplane;
import org.voo.api.models.City;

import java.util.List;

public interface AirplaneRepository extends CrudRepository<Airplane, String> {
    List<Airplane> findByCityId(long city_id);
}

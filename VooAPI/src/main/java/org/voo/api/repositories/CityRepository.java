package org.voo.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.voo.api.models.City;

public interface CityRepository extends CrudRepository<City, Long> {

    //City findById(long id);

}

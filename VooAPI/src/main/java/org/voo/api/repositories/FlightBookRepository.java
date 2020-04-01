package org.voo.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.voo.api.models.FlightBook;

public interface FlightBookRepository extends CrudRepository<FlightBook, Long> {
}

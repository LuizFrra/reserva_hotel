package org.voo.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.voo.api.models.FlyCompany;

import java.util.Optional;

public interface FlyCompanyRepository extends CrudRepository<FlyCompany, Long> {
    Optional<FlyCompany> findByName(String name);
}

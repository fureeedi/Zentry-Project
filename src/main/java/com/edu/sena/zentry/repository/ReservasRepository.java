package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.Reservas;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Reservas entity.
 */
@Repository
public interface ReservasRepository extends MongoRepository<Reservas, String> {}

package io.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.fa.models.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}

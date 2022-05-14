package io.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.fa.models.Carrier;

public interface CarrierRepository extends JpaRepository<Carrier, Long> {
    
}

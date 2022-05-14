package io.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.fa.models.Booking;

public  interface BookingRepository extends JpaRepository<Booking, Long> {
    
}

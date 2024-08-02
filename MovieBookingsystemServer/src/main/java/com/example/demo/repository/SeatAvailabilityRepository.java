package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.SeatAvailability;
@Repository
public interface SeatAvailabilityRepository extends JpaRepository<SeatAvailability, Long>{

}

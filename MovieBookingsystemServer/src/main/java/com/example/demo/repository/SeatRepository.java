package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Seat;
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>{

}

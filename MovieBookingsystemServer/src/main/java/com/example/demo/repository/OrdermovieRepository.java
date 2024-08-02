package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Ordermovie;
@Repository
public interface OrdermovieRepository extends JpaRepository<Ordermovie, Long>{

}

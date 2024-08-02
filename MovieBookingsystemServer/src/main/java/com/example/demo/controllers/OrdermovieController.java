package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidRequestException;
import com.example.demo.models.Ordermovie;
import com.example.demo.services.OrdermovieService;

@RestController
@RequestMapping("/api/ordermovies")
public class OrdermovieController {

    @Autowired
    private OrdermovieService ordermovieService;

    @GetMapping
    public List<Ordermovie> getAllOrders() {
        return ordermovieService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ordermovie> getOrderById(@PathVariable Long id) {
        try {
            Ordermovie ordermovie = ordermovieService.getOrderById(id);
            return ResponseEntity.ok(ordermovie);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Ordermovie> createOrder(@RequestBody Ordermovie ordermovie) {
        try {
            Ordermovie createdOrder = ordermovieService.saveOrder(ordermovie);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (InvalidRequestException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ordermovie> updateOrder(@PathVariable Long id, @RequestBody Ordermovie ordermovie) {
        try {
            ordermovie.setId(id);
            Ordermovie updatedOrder = ordermovieService.saveOrder(ordermovie);
            return ResponseEntity.ok(updatedOrder);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (InvalidRequestException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            ordermovieService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
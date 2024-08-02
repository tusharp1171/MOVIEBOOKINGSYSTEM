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
import com.example.demo.models.Seat;
import com.example.demo.services.SeatService;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id) {
        try {
            Seat seat = seatService.getSeatById(id);
            return ResponseEntity.ok(seat);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Seat> createSeat(@RequestBody Seat seat) {
        try {
            Seat createdSeat = seatService.saveSeat(seat);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSeat);
        } catch (InvalidRequestException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seat> updateSeat(@PathVariable Long id, @RequestBody Seat seat) {
        try {
            seat.setId(id);
            Seat updatedSeat = seatService.saveSeat(seat);
            return ResponseEntity.ok(updatedSeat);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (InvalidRequestException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        try {
            seatService.deleteSeat(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
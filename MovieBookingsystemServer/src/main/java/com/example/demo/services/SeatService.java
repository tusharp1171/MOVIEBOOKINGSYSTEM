package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidRequestException;
import com.example.demo.models.Seat;
import com.example.demo.repository.SeatRepository;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Seat getSeatById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + id));
    }

    public Seat saveSeat(Seat seat) {
        // Example validation, add more as needed
        if (seat.getSeatNumber() == null || seat.getSeatNumber().isEmpty()) {
            throw new InvalidRequestException("Seat number must not be empty.");
        }
        return seatRepository.save(seat);
    }

    public void deleteSeat(Long id) {
        if (!seatRepository.existsById(id)) {
            throw new EntityNotFoundException("Seat not found with id: " + id);
        }
        seatRepository.deleteById(id);
    }
}
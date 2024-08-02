package com.example.demo.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidRequestException;
import com.example.demo.models.Ordermovie;
import com.example.demo.repository.OrdermovieRepository;

@Service
public class OrdermovieService {

    @Autowired
    private OrdermovieRepository ordermovieRepository;

    public List<Ordermovie> getAllOrders() {
        return ordermovieRepository.findAll();
    }

    public Ordermovie getOrderById(Long id) {
        return ordermovieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }
  

    public Ordermovie saveOrder(Ordermovie ordermovie) {
        // Example validation, add more as needed
        if (ordermovie.getTotalPrice() <= 0) {
            throw new InvalidRequestException("Total price must be greater than zero.");
        }
        return ordermovieRepository.save(ordermovie);
    }

    public void deleteOrder(Long id) {
        if (!ordermovieRepository.existsById(id)) {
            throw new EntityNotFoundException("Order not found with id: " + id);
        }
        ordermovieRepository.deleteById(id);
    }
}
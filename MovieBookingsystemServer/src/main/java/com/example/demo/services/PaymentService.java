package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidRequestException;
import com.example.demo.models.Payment;
import com.example.demo.repository.PaymentRepository;

public class PaymentService {

	 @Autowired
	    private PaymentRepository paymentRepository;

	    public List<Payment> getAllPayments() {
	        return paymentRepository.findAll();
	    }

	    public Payment getPaymentById(Long id) {
	        return paymentRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));
	    }

	    public Payment savePayment(Payment payment) {
	        // Example validation, add more as needed
	        if (payment.getAmount() <= 0) {
	            throw new InvalidRequestException("Payment amount must be greater than zero.");
	        }
	        return paymentRepository.save(payment);
	    }

	    public void deletePayment(Long id) {
	        if (!paymentRepository.existsById(id)) {
	            throw new EntityNotFoundException("Payment not found with id: " + id);
	        }
	        paymentRepository.deleteById(id);
	    }
}

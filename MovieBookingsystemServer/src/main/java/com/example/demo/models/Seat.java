package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


	@Entity
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class Seat {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    private String seatNumber;

	    private boolean available;

	    @ManyToOne
	    @JoinColumn(name = "screen_id")
	    private Screen screen;

	    @ManyToOne
	    @JoinColumn(name = "booking_id")
	    private Booking booking;
	}
	
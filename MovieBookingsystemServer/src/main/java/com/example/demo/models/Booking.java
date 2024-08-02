package com.example.demo.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@NoArgsConstructor
@Table(name = "booking_order")
@AllArgsConstructor
public class Booking {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    @NotNull
	    private User user;

	    @ManyToOne
	    @JoinColumn(name = "movie_id")
	    @NotNull
	    private Movie movie;

	    @ManyToOne
	    @JoinColumn(name = "screen_id")
	    @NotNull
	    private Screen screen;

	    @ManyToMany
	    @JoinTable(
	        name = "booking_seats",
	        joinColumns = @JoinColumn(name = "booking_id"),
	        inverseJoinColumns = @JoinColumn(name = "seat_id")
	    )
	    private List<Seat> seats;
	   

	    @NotNull
	    private LocalDateTime bookingDate;

	    @NotNull
	    private double totalPrice;

	    @Enumerated(EnumType.STRING)
	    private BookingStatus status;  // e.g., "Confirmed", "Cancelled"

}

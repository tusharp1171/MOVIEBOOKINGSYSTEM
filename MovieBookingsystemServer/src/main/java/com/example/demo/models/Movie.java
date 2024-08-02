package com.example.demo.models;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    private String title;

	    private String genre;

	    private String duration;

	    private String director;

	    private String cast;

	    @NotBlank
	    private String synopsis;

	    private String showtime;

	    @OneToMany(mappedBy = "movie")
	    private List<Booking> bookings;

}

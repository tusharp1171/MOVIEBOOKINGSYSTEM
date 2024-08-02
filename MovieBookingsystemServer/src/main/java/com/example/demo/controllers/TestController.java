package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.Getuserid;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	 @Autowired
	    private Getuserid getuserid;
	
	
	@Autowired
	private UserRepository userRepository;
	
	
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
    
  }	
  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
  
  @GetMapping("/getuserid")
  public long getUserId(@RequestHeader("Authorization") String authorizationHeader) {
      String token = authorizationHeader.substring(7);
      return getuserid.getUserIdFromToken(token);
  }
  
  
  
  @GetMapping("/tokenusername")
  public Long tokenusername() {
	  String username = JwtUtils.usrenmaefortoken;
	  Optional<User> user=userRepository.findByUsername(username);
	  if(user.isPresent())
	  {
		User usera=user.get();
		return usera.getId();
	  }
	  return null;
	  
  }
  
}

package com.example.demo.controllers;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.example.demo.models.ERole;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import org.springframework.web.bind.annotation.GetMapping;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://192.168.14.19:4200")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;
  
  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  
 
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
      // Check if username is already taken
      if (userRepository.existsByUsername(signUpRequest.getUsername())) {
          return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
      }

      // Check if email is already in use
      if (userRepository.existsByEmail(signUpRequest.getEmail())) {
          return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
      }

      // Create new user entity
      User user = new User(signUpRequest.getUsername(),
              signUpRequest.getEmail(),
              encoder.encode(signUpRequest.getPassword()),
              signUpRequest.getMobile());
      // Set roles for the user
      Set<String> strRoles = signUpRequest.getRole();
      Set<Role> roles = new HashSet<>();

      if (strRoles == null) {
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
      } else {
          strRoles.forEach(role -> {
              switch (role) {
                  case "admin":
                      Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(adminRole);
                      break;
                  case "mod":
                      Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(modRole);
                      break;
                  default:
                      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(userRole);
              }
          });
      }
      user.setRoles(roles);

      // Save user including addresses
      userRepository.save(user);
      
     
      
      
      System.out.println(user.getId());
      
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  	
  @DeleteMapping("/deleteUser/{id}")
  public ResponseEntity<Map<String, String>> deleteUser(@PathVariable long id) {
      Optional<User> user = userRepository.findById(id);
      if (user.isPresent()) {
          userRepository.deleteById(id);
          Map<String, String> response = new HashMap<>();
          response.put("message", "User deleted successfully");
          return ResponseEntity.ok(response);
      } else {
          return ResponseEntity.notFound().build();
      }
  }


  
  
	  @GetMapping("/getUsersByRoleUser")
	  public ResponseEntity<List<User>> getUsersByRoleUser() {
	      List<User> users = userRepository.findAll();
	      List<User> filteredUsers = users.stream()
	                                      .filter(user -> user.getRoles().stream()
	                                                           .anyMatch(role -> role.getName() == ERole.ROLE_USER))
	                                      .collect(Collectors.toList());
	
	      if (filteredUsers.isEmpty()) {
	          return ResponseEntity.noContent().build(); 
	      }
	
	      return ResponseEntity.ok(filteredUsers); 
	  }
	  
}

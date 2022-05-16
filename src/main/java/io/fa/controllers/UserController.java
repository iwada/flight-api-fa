package io.fa.controllers;



import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.fa.models.User;
import io.fa.payload.response.MessageResponse;
import io.fa.payload.response.UserResponse;
import io.fa.repository.FlightRepository;
import io.fa.repository.UserRepository;
import io.fa.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api")
public class UserController {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/passengers")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> showPassengerDetails(@RequestParam("flightNumber") String flightNumber,
            @RequestParam("departureDate") String departureDate, @RequestParam(value="connectingFlights", required=false)String connectingFlights) {
                // Never Trust user input. Sanitize!
                 flightNumber = Jsoup.clean(flightNumber, Safelist.basic());
                 departureDate = Jsoup.clean(departureDate, Safelist.basic());
                List<User> userDetails = userService.getUserBookingDetails(flightNumber,departureDate,connectingFlights);
                return new ResponseEntity<>(userDetails, HttpStatus.OK);
        
    }

    @GetMapping("/passengers/{passengerId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> showPassengerDetailsRest(@PathVariable("passengerId") long passengerId) {
        User user = userService.findById(passengerId);
        User currentUser = userService.getCurrentUser();

        if(! userService.isUserOwnerOfResource(currentUser,user)){
            return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new MessageResponse("Error: You are not permited to view this resource."));
        }
       
        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Passender with ID: " + passengerId + " does not exist."));
        }
        return ResponseEntity.ok(new UserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBookings()));

    }

    

}

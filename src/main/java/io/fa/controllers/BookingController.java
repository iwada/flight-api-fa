package io.fa.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.fa.payload.request.BookingRequest;
import io.fa.payload.response.MessageResponse;
import io.fa.service.BookingService;
import io.fa.service.CarrierService;
import io.fa.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;

    @Autowired
    CarrierService carrierService;

    @PostMapping("{carrierId}/bookings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createBooking(@Valid @RequestBody BookingRequest bookingRequest,
            @PathVariable("carrierId") long carrierId) {

        // Check if departureDate && arrivalDate match the pattern YYYY/MM/DD
        if (!bookingService.isDateValid(bookingRequest.getFlights())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new MessageResponse("Wrong Date Format. format must be yyyy/mm/dd"));
        }

        // Check if departureDate and arrivalDate are not in the past
        if (!bookingService.isDateInFuture(bookingRequest.getFlights())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new MessageResponse("Dates can only be in the Future"));
        }

        // Check if Arrival date is after departure date
        if (!bookingService.isArrivalDateAfterDepartureDate(bookingRequest.getFlights())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new MessageResponse("An Arrival Date is before a Departure Date"));
        }

        // Saves Booking
        if (bookingService.saveBooking(bookingRequest.getName(), bookingRequest.getFlights(),
                userService.getCurrentUser(), carrierService.getCarrier(carrierId))) {
            return ResponseEntity.ok(new MessageResponse("Booking Made Successfully!"));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new MessageResponse("Error Saving Booking"));
    }

}
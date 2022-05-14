package io.fa.payload.response;

import java.util.Set;

import io.fa.models.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor 
public class BookingResponse {
    private Long bookingId;
    private String name;
    private Set<Flight> flights;
}

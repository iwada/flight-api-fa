package io.fa.payload.response;

import java.util.List;
import io.fa.models.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserResponse {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<Booking> bookings;  
}

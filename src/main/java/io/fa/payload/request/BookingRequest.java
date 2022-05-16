package io.fa.payload.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.fa.models.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookingRequest {
    @NotBlank(message = "Booking name is required")
    @Size(max = 20)
    private String name;

    @Valid
    private List<Flight> flights;
}

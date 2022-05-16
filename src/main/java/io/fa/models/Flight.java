package io.fa.models;


import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "flights")
public class Flight {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Size(max = 60)
  private String flightNumber;


  @NotBlank(message = "departureAirport is required")
  @Size(max = 3)
  private String departureAirport;


  @NotBlank(message = "arrivalAirport is required")
  @Size(max = 3)
  private String arrivalAirport;

  @NotBlank(message = "departureDate is required")
  @Size(max = 20)
  private String departureDate;

  @NotBlank(message = "arrivalDate is required")
  @Size(max = 20)
  private String arrivalDate;


  private boolean connectingFlight;


  @ManyToMany(mappedBy = "flights", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnoreProperties("flights")
  @JsonIgnore
  @Valid
  private List<Booking> bookings ;

  @OneToOne
  @JoinColumn(name="user_id", referencedColumnName = "id")
  @JsonIgnore
  private User user;



  public Flight(String departureAirport, String arrivalAirport, String departureDate, String arrivalDate){
    this.departureAirport = departureAirport;
    this.arrivalAirport = arrivalAirport;
    this.departureDate = departureDate;
    this.arrivalDate = arrivalDate;
  }
	
	
}

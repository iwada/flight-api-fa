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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

  @NotBlank
  @Size(max = 60)
  private String flightNumber;


  @NotBlank
  @Size(max = 3)
  private String departureAirport;


  @NotBlank
  @Size(max = 3)
  private String arrivalAirport;

  @NotBlank
  @Size(max = 20)
  private String departureDate;

  @NotBlank
  @Size(max = 20)
  private String arrivalDate;


  @ManyToMany(mappedBy = "flights", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnoreProperties("flights")
  @JsonIgnore
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

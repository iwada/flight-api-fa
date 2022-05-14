package io.fa.models;


import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("bookingId")
    private Long id;
  
    @NotBlank
    @Size(max = 20)
    @JsonIgnore
    private String name;

    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinTable(  name = "booking_flights", 
          joinColumns = @JoinColumn(name = "booking_id"), 
          inverseJoinColumns = @JoinColumn(name = "flight_id"))
    
    
    @JsonIgnoreProperties("booking")
    private List<Flight> flights;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @OneToOne
    @JoinColumn(name="carrier_id", referencedColumnName = "id")
    @JsonIgnore
    private Carrier carrier;


    public Booking(String name){
        this.name = name;
    }
    
    
}





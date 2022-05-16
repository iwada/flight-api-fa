package io.fa.service;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.fa.models.Booking;
import io.fa.models.Carrier;
import io.fa.models.Flight;
import io.fa.models.User;
import io.fa.repository.BookingRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class BookingService {

    LocalDate arrivalDate;
    LocalDate departureDate;
    DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
    String regex = "^\\d{4}\\/(0[1-9]|1[012])\\/(0[1-9]|[12][0-9]|3[01])$";
    Random random = new Random();

    @Autowired
    BookingRepository bookingRepository;

    /**
     * Checks and ensures that all dates passed in the request match the yyyy/mm/dd
     * format
     * 
     * @param date List of dates from individual flights
     * @return If the specified dates match the regex
     * 
     */
    public boolean isDateValid(List<Flight> date) {
        List<String> _d = new ArrayList<>();
        date.stream().forEach(
                e -> {
                    if (!e.getArrivalDate().matches(regex) || !e.getDepartureDate().matches(regex)) {
                        _d.add("1");
                    }
                });
        if (_d.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * Checks and ensures that the Arrival date is not before the departure date
     * 
     * @param dates List of dates from individual flights
     * @return If the specified dates are in order
     * 
     */
    public boolean isArrivalDateAfterDepartureDate(List<Flight> dates) {
        List<String> _d = new ArrayList<>();
        dates.stream().forEach(
                e -> {
                    arrivalDate = LocalDate.parse(e.getArrivalDate(), formatter);
                    departureDate = LocalDate.parse(e.getDepartureDate(), formatter);
                    if (arrivalDate.compareTo(departureDate) < 0) {
                        _d.add("1");
                    }
                });
        if (_d.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * Checks and ensures that the dates in the request are not in the future
     * 
     * @param date List of dates from individual flights
     * @return If the specified dates are in order
     * 
     */
    public boolean isDateInFuture(List<Flight> date) {
        List<String> _d = new ArrayList<>();
        Date currentDate = new Date();
        formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        date.stream().forEach(
                e -> {
                    if (e.getArrivalDate().matches(regex) && e.getDepartureDate().matches(regex)) {
                        arrivalDate = LocalDate.parse(e.getArrivalDate(), formatter);
                        departureDate = LocalDate.parse(e.getDepartureDate(), formatter);
                        if (!Date.from(arrivalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).after(currentDate)
                                || !Date.from(departureDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
                                        .after(currentDate)) { // Let's ensure flight dates is in the future
                            _d.add("1");
                        }
                    }
                });
        if (_d.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * Saves a booking with any passed in flights details
     * 
     * @param booking A Non unique name for this booking
     * @param flights A list of flights that belongs to this booking
     * @param user    An authenticated user that would be the owner of this booking
     * @param carrier A booking is made on a carrier
     * @return Returns true if the booking is successfully saved
     * 
     */
    public boolean saveBooking(String booking, List<Flight> flights, User user, Carrier carrier) {
        Booking _booking = new Booking(booking);
        for (int i = 0; i < flights.size(); i++) {
            Flight previousFlight = flights.get(i);
            if (i < flights.size() - 1) {
                Flight nextFlight = flights.get(i + 1);
                if (Objects.equals(previousFlight.getArrivalAirport(), nextFlight.getArrivalAirport())) {
                    previousFlight.setConnectingFlight(true);
                }
            }
            previousFlight.setFlightNumber(carrier.getCarrierCode() + random.nextInt(1000));
            previousFlight.setUser(user);
        }
        // flights.stream().forEach(u -> {
        // u.setFlightNumber(carrier.getCarrierCode() + random.nextInt(1000));
        // u.setUser(user);

        // });
        _booking.setCarrier(carrier);
        _booking.setFlights(flights);
        _booking.setUser(user);
        bookingRepository.save(_booking);
        return true;
    }

}

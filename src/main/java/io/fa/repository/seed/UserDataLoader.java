
package io.fa.repository.seed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.fa.models.Booking;
import io.fa.models.Carrier;
import io.fa.models.Flight;
import io.fa.models.Role;
import io.fa.models.RolesEnum;
import io.fa.models.User;
import io.fa.repository.UserRepository;
import io.fa.service.BookingService;
import io.fa.service.CarrierService;
import io.fa.service.RoleService;
import io.fa.service.UserService;

@Component
public class UserDataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    CarrierService carrierService;

    @Autowired
    BookingService bookingService;

    @Autowired
    RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        // Let's ensure that the database is empty - true on first load
        if (userRepository.count() == 0) {
            loadUserData();
            loadBookingData();
        }
    }

    private void loadUserData() {
        for (int i = 0; i < 11; i++) {
            User user = new User(
                    "fa-user" + i,
                    "fa-user" + i + "@example.com",
                    "password",
                    "John", "Doe");

            userService.saveUser(user, null);

        }
        // Let's make the last user an admin for test purposes
        User user = userRepository.findById(Long.valueOf(11)).get();
        Set<Role> roles = new HashSet<>();
        Role adminRole = roleService.findByName(RolesEnum.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("ROLE_NOT_FOUND"));
        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    private void loadBookingData() {
        List<Flight> flights = new ArrayList<>(List.of(
                new Flight("TLL", "HEL", "2048/12/02", "2098/12/02"),
                new Flight("HEM", "TAY", "2049/12/02", "2070/12/02"),
                new Flight("SDL", "FRA", "2023/12/02", "2027/12/02"),
                new Flight("LHR", "LIS", "2024/12/02", "2037/12/02")));
        Carrier carrier = carrierService.getCarrier(Long.valueOf(1));
        User user = userRepository.findById(Long.valueOf(1)).get();
        bookingService.saveBooking("Preloaded Booking", flights, user, carrier);

    }

}

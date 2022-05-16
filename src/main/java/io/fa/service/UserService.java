package io.fa.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.fa.models.Flight;
import io.fa.models.Role;
import io.fa.models.RolesEnum;
import io.fa.models.User;

import io.fa.repository.UserRepository;
import io.fa.security.services.UserDetailsImpl;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleService roleService;

  @Autowired
  private PasswordEncoder encoder;

  private static final String ROLE_NOT_FOUND = "Error: Role is not found.";

  /**
   * Checks and ensures that a provider username is unique
   * 
   * @param username the provided username
   * @return True if the username does not exist
   * 
   */
  public boolean userNameExists(String username) {
    return Boolean.TRUE.equals(userRepository.existsByUsername(username));
  }

  /**
   * Checks and ensures that a provider email is unique
   * 
   * @param email the provided email
   * @return True if the email does not exist
   * 
   */
  public boolean emailExists(String email) {
    return Boolean.TRUE.equals(userRepository.existsByEmail(email));
  }

  /**
   * Checks and ensures that a provider username is unique
   * 
   * @param username the provided username
   * @param email    the provided email
   * @return True if the username and email does not exist
   * 
   */
  public boolean userNameOrEmailExists(String username, String email) {
    return userNameExists(username) || emailExists(email);
  }

  /**
   * Saves a User with any passed in roles. If not Role is passed is,
   * the user defaults a non administrative role
   * 
   * @param user     A built user Object
   * @param strRoles A list of roles for this user. [admin] for the admin role.
   * @return Returns true if the user is successfully saved
   * 
   */
  public boolean saveUser(User user, Set<String> strRoles) {
    Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      Role userRole = roleService.findByName(RolesEnum.ROLE_USER)
          .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        if ("admin".equals(role)) {
          Role adminRole = roleService.findByName(RolesEnum.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
          roles.add(adminRole);
        }
      });
    }

    User user_ = new User(user.getUsername(),
        user.getEmail(),
        encoder.encode(user.getPassword()));

    user_.setRoles(roles);
    user_.setFirstName(user.getFirstName());
    user_.setLastName(user.getLastName());
    userRepository.save(user_);
    return true;
  }

  /**
   * Utility Method to return the currently logged in user
   * 
   * @return Returns currently logged in user
   * 
   */
  public User getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    Optional<User> optUser = userRepository.findById(userDetails.getId());
    if (optUser.isPresent()) {
      return optUser.get();
    }
    return new User();
  }

  /**
   * Returns the booking details(including flights) for All Avaliable users
   * matching the passed in parameters
   * A Single database call is made, and the list is filtered inplace for better
   * perfoamce.
   * 
   * @param flightNumber  a flight Number for which the booking details would be
   *                      needed
   * @param departureDate a departure date for which the booking details would be
   *                      needed
   * @return Returns a filtered list matching the required critetia.
   * 
   */
  public List<User> getUserBookingDetails(String flightNumber, String departureDate, String connectingFlight) {
    if (!connectingFlight.isBlank()) {
      Predicate<Flight> searchParams = e -> !Objects.equals(e.getFlightNumber(), flightNumber) &&
          !Objects.equals(e.getDepartureDate(), departureDate) && !e.isConnectingFlight();
    }
    Predicate<Flight> searchParams = e -> !Objects.equals(e.getFlightNumber(), flightNumber) &&
        !Objects.equals(e.getDepartureDate(), departureDate);

    List<User> userDetails = userRepository.findAll();
    userDetails.forEach(user -> user.getBookings().forEach(booking -> booking.getFlights().removeIf(searchParams)));
    return userDetails;
  }

  

  public User findById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  /**
   * A Simple Authorization check to ensure that for non admin authenticated users
   * can not have access to resources that are not theres.
   * 
   * @param currentUser The currently authenticated user
   * @param user        The user requesting access to a resource
   * @return Short circuits and returns true, if current user has an admin role.
   *         It also returns True or false depending of the resource ownership
   * 
   */
  public boolean isUserOwnerOfResource(User currentUser, User user) {
    // if currentUser is admin, this check is needless. S(he) should always own all
    // resources?
    if ("ROLE_ADMIN".equals(String.valueOf(currentUser.getRoles().iterator().next().getName()))) {
      return true;
    }
    return Objects.equals(currentUser.getId(), user.getId());
  }

}

package io.fa.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.fa.models.Role;
import io.fa.models.RolesEnum;
import io.fa.models.User;

import io.fa.repository.UserRepository;

@Service
public class UserService {

  @Autowired  
  private  UserRepository userRepository;

  @Autowired
  private RoleService roleService;

  @Autowired
  private PasswordEncoder encoder;


  private static final String ROLE_NOT_FOUND = "Error: Role is not found.";

   // Returns True if username already exists
   public boolean userNameExists(String username){
    return Boolean.TRUE.equals(userRepository.existsByUsername(username));
   }

   // Returns True if email already exists
   public boolean emailExists(String email){
    return Boolean.TRUE.equals(userRepository.existsByEmail(email));
   }

   // Returns True iff both methods return true
   public boolean userNameOrEmailExists(String username,String email){
        return userNameExists(username) || emailExists(email);
   }

   // Saves the user with accompanying strRoles
   public boolean saveUser(User user, Set<String> strRoles){
    Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      Role userRole = roleService.findByName(RolesEnum.ROLE_USER)
          .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        if ("admin".equals(role)){
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
   
}

package io.fa.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupRequest {
  @NotBlank(message = "username is required") @Size(min = 3, max = 20, message = "username must be between 3 and 20 characters")
  private String username;

  @NotBlank(message = "email is required") @Size(max = 50) @Email(message = "email must be valid")
  private String email;

  @NotBlank(message = "password is required") @Size(min = 6, max = 40, message="password must be between 6 and 40 characters")
  private String password;

  @NotBlank(message = "lastName is required")
  private String lastName;

  @NotBlank(message = "firstName is required")
  private String firstName;

  private Set<String> role;

  
 
}

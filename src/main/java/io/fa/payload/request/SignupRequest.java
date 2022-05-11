package io.fa.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupRequest {
  @NotBlank @Size(min = 3, max = 20)
  private String username;

  @NotBlank @Size(max = 50) @Email
  private String email;

  @NotBlank @Size(min = 6, max = 40)
  private String password;

  @NotBlank
  private String lastName;

  @NotBlank
  private String firstName;

  private Set<String> role;

  
 
}

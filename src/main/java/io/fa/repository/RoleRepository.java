package io.fa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.fa.models.RolesEnum;
import io.fa.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RolesEnum name);
}

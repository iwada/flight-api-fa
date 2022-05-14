package io.fa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.fa.models.Role;
import io.fa.models.RolesEnum;
import io.fa.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findByName(RolesEnum roleName){
        return roleRepository.findByName(roleName);
    }

}

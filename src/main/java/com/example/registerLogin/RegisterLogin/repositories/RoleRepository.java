package com.example.registerLogin.RegisterLogin.repositories;

import com.example.registerLogin.RegisterLogin.models.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    // metodo personalizado
    Optional<Role> findByName(String name);
    // ^ El nombre debe ser con palabras clave -> findBy....
}

package com.example.registerLogin.RegisterLogin.repositories;

import com.example.registerLogin.RegisterLogin.models.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    // metodo personalizado
    Optional<User> findByUsername(String username);
    // ^ El nombre debe ser con palabras clave -> findBy....
}

package com.example.registerLogin.RegisterLogin.services;

import com.example.registerLogin.RegisterLogin.models.UserRequest;
import com.example.registerLogin.RegisterLogin.models.entities.User;
import com.example.registerLogin.RegisterLogin.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // retorna todos los objetos de la tabla
    List<User> findAll();
    // retorna un optional que nos permite ver diferentes estados del objeto
    // existe? vacio? ---
    Optional<User> findById(Long id);

    // crea o modifica dependiendo del estado del objeto
    // retorna el user con el id asignado
    User save(User user);
    Optional<User> update(UserRequest user, Long id);

    void remove(Long id);
}

package com.example.registerLogin.RegisterLogin.services;

import com.example.registerLogin.RegisterLogin.models.UserRequest;
import com.example.registerLogin.RegisterLogin.models.entities.Role;
import com.example.registerLogin.RegisterLogin.models.entities.User;
import com.example.registerLogin.RegisterLogin.repositories.RoleRepository;
import com.example.registerLogin.RegisterLogin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    // tener cuidado con los metodos de lectura
    // readOnly para metodos que no modifican data
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional // <-- no es necesario el readonly porque estamos modificando
    public User save(User user) {
        // codificando password y pasando al modelo
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> opt = roleRepository.findByName("ROLE_USER");

        // creando lista de roles
        List<Role> roles = new ArrayList<>();

        if (opt.isPresent()){
            roles.add(opt.orElseThrow());
        }

        // agregamos la lista al user
        user.setRoles(roles);


        // guarda data en bd
        return repository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(UserRequest user, Long id) {
        // debemos obtener el usuario que queremos editar
        Optional<User> o = findById(id);
        // actualizamos solo los campos necesarios
        if (o.isPresent()){

            User userDb = o.orElseThrow();
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            // guardamos el nuevo user
            this.save(userDb);

            return Optional.of(userDb);
        }
        return Optional.empty(); // retorna un empty si no encuentra data
    }

    @Override
    @Transactional // <-- no es necesario el readonly porque estamos modificando
    public void remove(Long id) {
        repository.deleteById(id);
    }
}

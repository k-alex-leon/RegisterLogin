package com.example.registerLogin.RegisterLogin.services;

import com.example.registerLogin.RegisterLogin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// ESTA CLASE SE ENCARGA DE VALIDAR QUE LOS DATOS DEL USUARIO CONCUERDEN CON LOS DATOS EN LA DB
@Service // <- esta clase DEBE ser un servicio
public class JpaUserDetailsService implements UserDetailsService {

    // obtenemos del repository el metodo para encontrar el user
    @Autowired
    private UserRepository repository;


    @Override
    @Transactional(readOnly = true)// <- transaction de solo lectura
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // obtenemos user desde metodo del repository
        Optional<com.example.registerLogin.RegisterLogin.models.entities.User> o =
                repository.findByUsername(username);

        // en caso de que el user no exista
        if (!o.isPresent()){
            throw new UsernameNotFoundException(String.format("User %s doesn't exist!", username));
        }

        com.example.registerLogin.RegisterLogin.models.entities.User user = o.orElseThrow();

        // lista de roles
        List<GrantedAuthority> authorities = new ArrayList<>();
        // un rol siempre es escrito de la siguiente manera ->
        // -> "ROLE + _ + NOMBRE_DEL_ROL"
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));


        return new User(user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}

package com.example.registerLogin.RegisterLogin.controllers;

import com.example.registerLogin.RegisterLogin.models.UserRequest;
import com.example.registerLogin.RegisterLogin.models.entities.User;
import com.example.registerLogin.RegisterLogin.repositories.UserRepository;
import com.example.registerLogin.RegisterLogin.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list(){
        return service.findAll();
    }

    @GetMapping("/{id}") // <- {id} = path variable
    public ResponseEntity<?> show(@PathVariable Long id)// <- el id que pasamos como ruta lo usaremos como parametro
    {
        Optional<User> userOptional = service.findById(id);
        if (userOptional.isPresent()) {
            // si existe retornamos el user
        return ResponseEntity.ok(userOptional.get());
        }

        return ResponseEntity.notFound().build(); // <- retorna una ex 404 (data not found)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // <- val 201 : success (objeto creado)
    // @Valid se encarga de validar el objeto que viene como parametro
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) // <- obtenemos un JSON que se convierte en la entity
    {
        if (result.hasErrors()) return validation(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest user, BindingResult result, @PathVariable Long id){

        if (result.hasErrors()) return validation(result);

        Optional<User> o = service.update(user, id);
        // actualizamos solo los campos necesarios
        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }
        return ResponseEntity.notFound().build(); // <- si no encuentra el obj retorna un 404
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) // <- el id que pasamos como ruta lo usaremos como parametro
    {
        Optional<User> obj = service.findById(id);
        if (obj.isPresent()) {
        service.remove(id);
        return ResponseEntity.noContent().build(); // <- retorna un 204 (sin contenido)
        }
        return ResponseEntity.notFound().build(); // retorna 404 (not found)
    }

    // validacion de campos
    private ResponseEntity<?> validation(BindingResult result) {
        // creamos una lista para almacenar los errores
        Map<String, String> errors = new HashMap<>();

        // itera el result y por cada campo lo agrega a la lista de errores
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(),
                    "Error: " +
                    error.getField() +
                    " " +
                    error.getDefaultMessage());
        });

        // retornamos los errores
        // badRequest retorna error 400
        return ResponseEntity.badRequest().body(errors);
    }

}

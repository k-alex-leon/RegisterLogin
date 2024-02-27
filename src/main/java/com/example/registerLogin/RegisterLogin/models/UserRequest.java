package com.example.registerLogin.RegisterLogin.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

// ESTA CLASE HA SIDO CREADA SOLO PARA EDITAR EL USUARIO
// esto nos ayuda a evitar la validacion del password y evita un error 400 con @PUTMAPPING
public class UserRequest {

    @NotEmpty
    private String username;
    @NotBlank
    @Email
    private String email;

    public UserRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
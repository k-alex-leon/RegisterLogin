package com.example.registerLogin.RegisterLogin.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column para valores unicos (no se pueden repetir)
    @Column(unique = true) // <- valor unico (no se puede repetir)
    @NotEmpty // <- valida que no este vacio pero permite espacios '  '
    private String username;
    @Size(min = 6) // <- valida el size del password (min 6 chars | max 10 chars)
    @NotBlank // <- solo aplica para Strings
    private String password;

    @Column(unique = true)
    @NotBlank // <- valida que no este vacio y no permite espacios
    @Email // <- valida el email (debe estar acompañado de @Notblack || @NotEmpty)
    private String email;

    // TABLA DE INTERMEDIO -> Join table
    // -> es una tabla generada para guardar las llaves foraneas del user y el role
    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
    private List<Role> roles;

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}

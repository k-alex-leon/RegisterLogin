package com.example.registerLogin.RegisterLogin.auth;

import com.example.registerLogin.RegisterLogin.auth.filters.JwtAuthenticationFilter;
import com.example.registerLogin.RegisterLogin.auth.filters.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;


    @Bean
    PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder -> una vez encriptado no se puede revertir a diferencia de Base64
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Damos permiso al metodo GET para obtener datos ->
    // quiere decir que un usuario no necesita hacer login para obtener datos
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authRules -> authRules
                // autoriza obtener los usuarios
                // otros metodos como .PUT requieren login (Bearer token auth)
                .requestMatchers(HttpMethod.GET, "/users")
                .permitAll()
                .anyRequest()
                .authenticated())
                .csrf(config -> config.disable())
                // agregando filtro de la clase JwtAuthenticationFilter
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .sessionManagement(mnt -> mnt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}

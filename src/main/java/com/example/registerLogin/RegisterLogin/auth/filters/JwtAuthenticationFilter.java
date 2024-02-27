package com.example.registerLogin.RegisterLogin.auth.filters;

import com.example.registerLogin.RegisterLogin.models.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.registerLogin.RegisterLogin.auth.TokenJwtConfig.*;

// este filtro no ayuda a determinar el auth de los usuarios
// este filtro es llamado en los metodos POST y en el LOGIN de los usuarios
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // hereda algunos metodos del UsernamePasswordAuthenticationFilter



    private AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    } // <- constructor auto-generado (alt + Ins)

    // metodo login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        User user = new User();
        String password = "";
        String username = "";


        // obtenemos datos del usuario que vienen como request en los parametros
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();

            // ¿logger? 🤔
            /*logger.info("username from request input stream: " + username);
            logger.info("password from request input stream: " + password);*/
            // ^ eliminar al lanzar a produccion ^

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);


        return authenticationManager.authenticate(authToken);
    }

    // registro exitoso del usuario
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
                                            throws IOException, ServletException {
        // hacemos un cast a getPrincipal() para obtener datos del usuario
        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();


        // El token generardo solo estara activo 1 hora -> .expiration()
        String token = Jwts.builder()
                // pasamos como payload la data del user
                .subject(username)
                // firma
                .signWith(SECRET_KEY)
                // fecha de creacion del token
                .issuedAt(new Date())
                // agregando 1h a el tiempo en el que se genera el token
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .compact();

        // pasamos token al header
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        // creamos un objeto
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("message", String.format("Hi %s, login success", username));
        body.put("username", username);

        // el objeto body lo convertimos a JSON
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        // retornamos un success result = 200
        response.setStatus(200);
        response.setContentType("application/json");
    }

    // algo sale mal con el registro de usuario
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        // creamos un obj con los atrib de message y error
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Login error! Wrong username or password...");
        // obtenemos el error
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }
}

package com.example.registerLogin.RegisterLogin.auth;

// import io.jsonwebtoken.*;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class TokenJwtConfig {
    // public final static String SECRET_KEY = "this_is_a_token";

    // genera una llave unica
    public final static SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public final static String PREFIX_TOKEN = "Bearer ";
    public final static String HEADER_AUTHORIZATION = "Authorization";


}

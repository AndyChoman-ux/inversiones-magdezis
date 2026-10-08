package com.magdezis.service;

public class AuthException extends RuntimeException {
    public AuthException(String mensaje) {
        super(mensaje);
    }
}

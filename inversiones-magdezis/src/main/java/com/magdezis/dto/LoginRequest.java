package com.magdezis.dto;

public class LoginRequest {
    private final String correo;
    private final String password;

    public LoginRequest(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    public String getCorreo() { return correo; }
    public String getPassword() { return password; }
}

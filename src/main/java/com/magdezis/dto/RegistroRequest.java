package com.magdezis.dto;

public class RegistroRequest {
    private final String nombre;
    private final String apellido;
    private final String correo;
    private final String password;

    public RegistroRequest(String nombre, String apellido, String correo, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.password = password;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getPassword() { return password; }
}

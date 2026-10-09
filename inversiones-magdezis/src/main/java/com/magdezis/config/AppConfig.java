package com.magdezis.config;

import com.magdezis.repository.UsuarioRepositoryJdbc;
import com.magdezis.service.AuthService;

public final class AppConfig {

    private static final AuthService AUTH_SERVICE = new AuthService(new UsuarioRepositoryJdbc());

    private AppConfig() { }

    public static AuthService authService() {
        return AUTH_SERVICE;
    }
}

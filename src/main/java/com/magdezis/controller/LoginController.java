package com.magdezis.controller;

import com.magdezis.config.AppConfig;
import com.magdezis.dto.LoginRequest;
import com.magdezis.model.Usuario;
import com.magdezis.repository.DataAccessException;
import com.magdezis.service.AuthException;
import com.magdezis.service.AuthService;
import com.magdezis.view.Alertas;
import com.magdezis.view.Navegador;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;

    private final AuthService authService = AppConfig.authService();

    @FXML
    private void onIniciarSesion() {
        try {
            Usuario usuario = authService.login(
                    new LoginRequest(txtCorreo.getText(), txtPassword.getText()));
            Alertas.info("Inicio de sesión",
                    "Bienvenido " + usuario.getNombre() + ".\nRol: " + usuario.getRol());
            txtPassword.clear();
        } catch (AuthException e) {
            Alertas.error("Error", e.getMessage());
        } catch (DataAccessException e) {
            LOGGER.log(Level.SEVERE, "Error de acceso a datos en el login", e);
            Alertas.error("Error", "No fue posible conectar con la base de datos.");
        }
    }

    @FXML
    private void onIrARegistro() {
        Navegador.mostrar("registro.fxml", "Registro - Inversiones Magdezis");
    }
}

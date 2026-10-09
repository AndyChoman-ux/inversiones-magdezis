package com.magdezis.controller;

import com.magdezis.config.AppConfig;
import com.magdezis.dto.RegistroRequest;
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

public class RegistroController {

    private static final Logger LOGGER = Logger.getLogger(RegistroController.class.getName());

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;

    private final AuthService authService = AppConfig.authService();

    @FXML
    private void onRegistrar() {
        try {
            authService.registrar(new RegistroRequest(
                    txtNombre.getText(), txtApellido.getText(),
                    txtCorreo.getText(), txtPassword.getText()));
            Alertas.info("Registro", "Usuario registrado correctamente.");
            onIrALogin();
        } catch (AuthException e) {
            Alertas.error("Error", e.getMessage());
        } catch (DataAccessException e) {
            LOGGER.log(Level.SEVERE, "Error de acceso a datos en el registro", e);
            Alertas.error("Error", "No fue posible conectar con la base de datos.");
        }
    }

    @FXML
    private void onIrALogin() {
        Navegador.mostrar("login.fxml", "Login - Inversiones Magdezis");
    }
}

package com.magdezis.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class Alertas {

    private Alertas() { }

    public static void info(String titulo, String mensaje) {
        mostrar(AlertType.INFORMATION, titulo, mensaje);
    }

    public static void error(String titulo, String mensaje) {
        mostrar(AlertType.ERROR, titulo, mensaje);
    }

    private static void mostrar(AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

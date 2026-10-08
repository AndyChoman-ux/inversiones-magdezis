package com.magdezis.view;

import java.io.IOException;
import java.io.UncheckedIOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class Navegador {

    private static Stage stage;

    private Navegador() { }

    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void mostrar(String archivoFxml, String titulo) {
        try {
            Parent root = FXMLLoader.load(Navegador.class.getResource("/view/" + archivoFxml));
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo cargar la vista " + archivoFxml, e);
        }
    }
}

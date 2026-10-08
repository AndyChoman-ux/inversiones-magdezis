package com.magdezis;

import com.magdezis.view.Navegador;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Navegador.init(stage);
        stage.setResizable(false);
        Navegador.mostrar("login.fxml", "Login - Inversiones Magdezis");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package it.unicam.cs.pa.jlogo.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/logo_main.fxml")));
        primaryStage.setScene(new Scene(root, LogoMainController.DEFAULT_WIDTH, LogoMainController.DEFAULT_HEIGHT));
        primaryStage.show();
    }
}
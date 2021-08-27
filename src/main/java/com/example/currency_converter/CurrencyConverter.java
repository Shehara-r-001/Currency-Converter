package com.example.currency_converter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class CurrencyConverter extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CurrencyConverter.class.getResource("initial_scene.fxml"));

        Image icon = new Image("cc_logoo.png");
        stage.getIcons().add(icon);
        Scene scene = new Scene(fxmlLoader.load(), 640, 580);
        stage.setTitle("Currency Converter..");

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });
    }

    public void exit(Stage stage){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm the closing..");
        alert.setHeaderText("Do you want to exit?");

        if (alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }


}
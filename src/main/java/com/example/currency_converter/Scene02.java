package com.example.currency_converter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene02 {

    private Parent parent;
    private Scene scene;
    private Stage stage;

    @FXML
    Button u_to_g;

    @FXML
    Button u_to_r;

    @FXML
    Button r_to_g;

    public void to_scene_UtoG(ActionEvent event) throws IOException {

        parent = FXMLLoader.load(getClass().getResource("scene_UG.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void to_scene_UtoR(ActionEvent event) throws IOException {

        parent = FXMLLoader.load(getClass().getResource("scene_RU.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void to_scene_RtoG(ActionEvent event) throws IOException {

        parent = FXMLLoader.load(getClass().getResource("scene_RG.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void to_custom(ActionEvent event) throws IOException {

        parent = FXMLLoader.load(getClass().getResource("custom.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}

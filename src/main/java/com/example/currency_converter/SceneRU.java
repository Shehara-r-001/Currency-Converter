package com.example.currency_converter;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import org.json.JSONObject;

public class SceneRU implements Initializable {

    Parent parent;
    Scene scene;
    Stage stage;

    @FXML
    Label title_ru, curr_type_in_ru, result_in_ru;

    @FXML
    Button cal_in_ru;

    @FXML
    TextField field_in_ru;

    @FXML
    ChoiceBox choice_in_ru;

    private String[] currency = {"LKR", "USD"};
    Double entered;
    int flag;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choice_in_ru.getItems().addAll(currency);
        choice_in_ru.setOnAction(this::calculate_ru);
        //choice_in_ru.getSelectionModel().selectFirst();
    }

    public void calculate_ru(Event event) {

        if ( choice_in_ru.getValue() != null){

            try {
                entered = Double.parseDouble(field_in_ru.getText());

                getDataFromAPI(curr_type_in_ru, result_in_ru, flag);
            }
            catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please add valid numbers only");
                alert.showAndWait();
            }
            catch (Exception e){
                System.out.println(e);
            }

            if ( choice_in_ru.getValue().equals("LKR")){
                flag = 0;
            }
            else if ( choice_in_ru.getValue().equals("USD")){
                flag = 1;
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select the currency type first");
            alert.showAndWait();
        }
    }

    private void getDataFromAPI(Label curr_type_in_ru, Label result_in_ru, int flag) throws IOException {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        URL url = new URL("http://api.exchangeratesapi.io/v1/latest?access_key=f7b094c94103b26551de9ac2e4bbd6e5&symbols=LKR,USD&format=1");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int response_code = httpURLConnection.getResponseCode();

        if ( response_code == HttpURLConnection.HTTP_OK){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            StringBuffer stringBuffer = new StringBuffer();

            while (( line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }bufferedReader.close();

            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            JSONObject rates = jsonObject.getJSONObject("rates");

            Double lkr_rate = rates.getDouble("LKR");
            Double usd_rate = rates.getDouble("USD");

            if ( flag == 0){
                Double lkr_to_usd = ( entered * usd_rate)/lkr_rate;
                curr_type_in_ru.setText("USD :");
                result_in_ru.setText(decimalFormat.format(lkr_to_usd));
            }
            else if ( flag == 1){
                Double usd_to_lkr = ( entered * lkr_rate)/usd_rate;
                curr_type_in_ru.setText("LKR :");
                result_in_ru.setText(decimalFormat.format(usd_to_lkr));
            }
        }
    }

    public void previous_screen(ActionEvent event) throws IOException {

        parent = FXMLLoader.load(getClass().getResource("scene02.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        
    }
}

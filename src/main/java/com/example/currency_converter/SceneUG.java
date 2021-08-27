package com.example.currency_converter;

import javafx.event.ActionEvent;
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
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import org.json.JSONObject;

public class SceneUG implements Initializable {

    Parent parent;
    Scene scene;
    Stage stage;

    @FXML
    Label title_in_ug, curr_type_in_UG, result_in_ug;

    @FXML
    ChoiceBox<String> choice_in_ug;

    @FXML
    TextField field_in_ug;

    @FXML
    Button cal_in_ug;

    Double entered;
    int flag;

    private String[] currency = {"USD", "GBP"};

    public void calculate_ug(ActionEvent event){

        if ( choice_in_ug.getValue() != null) {


            try {

                entered = Double.parseDouble(field_in_ug.getText());
                System.out.println(entered);

                field_in_ug.setPromptText("Amount");

                getDataFromAPI(curr_type_in_UG, result_in_ug, flag);


            }
            catch (NumberFormatException nfe) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please add a valid number");
                alert.showAndWait();

            }
            catch (Exception e) {
                System.out.println(e);
            }

            if (choice_in_ug.getValue().equals("USD")) {
                flag = 0;
            } else if (choice_in_ug.getValue().equals("GBP")) {
                flag = 1;
            }

        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select the currency type first");
            alert.showAndWait();

        }


    }



    @Override
    public void initialize(URL location, ResourceBundle resources){
        choice_in_ug.getItems().addAll(currency);
        choice_in_ug.setOnAction(this::calculate_ug);
        //choice_in_ug.getSelectionModel().selectFirst();
    }

    public void previous_screen(ActionEvent event) throws IOException {

        parent = FXMLLoader.load(getClass().getResource("scene02.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    private void getDataFromAPI(Label curr_type_in_UG, Label result_in_ug, int flag) throws IOException {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        URL url = new URL("http://api.exchangeratesapi.io/v1/latest?access_key=f7b094c94103b26551de9ac2e4bbd6e5&symbols=USD,GBP&format=1");
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

            Double usd_rate = rates.getDouble("USD");
            Double gbp_rate = rates.getDouble("GBP");

            if (flag == 0){

                Double usd_to_gbp_result = (entered * gbp_rate)/usd_rate;
                curr_type_in_UG.setText("GBP :");
                result_in_ug.setText(decimalFormat.format(usd_to_gbp_result));

            }else if ( flag == 1){

                Double gbp_to_usd_result = (entered * usd_rate)/gbp_rate;
                curr_type_in_UG.setText("USD :");
                result_in_ug.setText(decimalFormat.format(gbp_to_usd_result));

            }

        }
    }
}

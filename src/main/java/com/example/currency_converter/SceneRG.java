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
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import org.json.JSONObject;

public class SceneRG implements Initializable {

    Parent parent;
    Stage stage;
    Scene scene;

    @FXML
    Button cal_in_rg;

    @FXML
    Label title_in_rg, curr_type_in_rg, result_in_rg;

    @FXML
    TextField field_in_rg;

    @FXML
    ChoiceBox choice_in_rg;

    Double entered;
    int flag;

    private String[] currency = { "LKR", "GBP"};


    public void calculate_rg(Event event) {
        if ( choice_in_rg.getValue() != null ){
            try{
                entered = Double.parseDouble(field_in_rg.getText());
                //prompt text
                System.out.println(entered);
                getDataFromAPI(curr_type_in_rg, result_in_rg, flag);

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

            if ( choice_in_rg.getValue().equals("LKR")){
                flag = 0;
                System.out.println("LKR");
            }
            else if ( choice_in_rg.getValue().equals("GBP")){
                flag = 1;
                System.out.println("GBP");
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select the currency type first");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choice_in_rg.getItems().addAll(currency);
        choice_in_rg.setOnAction(this::calculate_rg);
    }



    public void previous_screen(ActionEvent event) throws IOException{

        parent = FXMLLoader.load(getClass().getResource("scene02.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }


    private void getDataFromAPI(Label curr_type_in_rg, Label result_in_rg, int flag) throws IOException {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        URL url = new URL("http://api.exchangeratesapi.io/v1/latest?access_key=f7b094c94103b26551de9ac2e4bbd6e5&symbols=LKR,GBP&format=1");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int response_code = httpURLConnection.getResponseCode();

        if ( response_code == HttpURLConnection.HTTP_OK){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            StringBuffer stringBuffer = new StringBuffer();

            while ( ( line = bufferedReader.readLine()) != null ){
                stringBuffer.append(line);
            }bufferedReader.close();

             JSONObject jsonObject = new JSONObject(stringBuffer.toString());
             JSONObject rates = jsonObject.getJSONObject("rates");

             Double lkr_rate = rates.getDouble("LKR");
             Double gbp_rate = rates.getDouble("GBP");

             if ( flag == 0 ){
                 //System.out.println(lkr_rate);
                 Double lkr_to_gbp = ( entered * gbp_rate)/lkr_rate;
                 curr_type_in_rg.setText("GBP :");
                 result_in_rg.setText(decimalFormat.format(lkr_to_gbp));
             }
             else if ( flag == 1){
                 //System.out.println(gbp_rate);
                 Double gbp_to_lkr = ( entered * lkr_rate)/gbp_rate;
                 curr_type_in_rg.setText("LKR :");
                 result_in_rg.setText(decimalFormat.format(gbp_to_lkr));
             }
        }
    }
}

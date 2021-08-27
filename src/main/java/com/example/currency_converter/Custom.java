package com.example.currency_converter;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class Custom implements Initializable {

    Parent parent;
    Scene scene;
    Stage stage;

    @FXML
    ChoiceBox choice_in_cu, result_curr;

    @FXML
    Label title_cu, result_in_cu;

    @FXML
    TextField field_in_cu;

    Double entered;
    int flag_1, flag_2;

    private String[] currency = {"LKR", "INR", "EUR","USD", "GBP", "AUD", "CHF", "CAD", "JPY"};


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        choice_in_cu.getItems().addAll(currency);
        choice_in_cu.setOnAction(this::calculate_custom);

        result_curr.getItems().addAll(currency);
        result_curr.setOnAction(this::calculate_custom);
    }

    public void calculate_custom(Event event) {

        if ( (choice_in_cu.getItems() != null) && (result_curr.getItems() != null)){

            try{
                entered = Double.parseDouble(field_in_cu.getText());

                getDataFromAPI(result_in_cu, flag_1, flag_2);
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

            if ( choice_in_cu.getValue().equals("LKR")){
                flag_1 = 0;
            }
            else if ( choice_in_cu.getValue().equals("INR")){
                flag_1 = 1;
            }
            else if ( choice_in_cu.getValue().equals("USD")){
                flag_1 = 2;
            }
            else if ( choice_in_cu.getValue().equals("EUR")){
                flag_1 = 3;
            }
            else if ( choice_in_cu.getValue().equals("GBP")){
                flag_1 = 4;
            }
            else if ( choice_in_cu.getValue().equals("AUD")){
                flag_1 = 5;
            }
            else if ( choice_in_cu.getValue().equals("CAD")){
                flag_1 = 6;
            }
            else if ( choice_in_cu.getValue().equals("CHF")){
                flag_1 = 7;
            }
            else if ( choice_in_cu.getValue().equals("JPY")){
                flag_1 = 8;
            }

            if ( result_curr.getValue().equals("LKR")){
                flag_2 = 0;
            }
            else if ( result_curr.getValue().equals("INR")){
                flag_2 = 1;
            }
            else if ( result_curr.getValue().equals("USD")){
                flag_2 = 2;
            }
            else if ( result_curr.getValue().equals("EUR")){
                flag_2 = 3;
            }
            else if ( result_curr.getValue().equals("GBP")){
                flag_2 = 4;
            }
            else if ( result_curr.getValue().equals("AUD")){
                flag_2 = 5;
            }
            else if ( result_curr.getValue().equals("CAD")){
                flag_2 = 6;
            }
            else if ( result_curr.getValue().equals("CHF")){
                flag_2 = 7;
            }
            else if ( result_curr.getValue().equals("JPY")){
                flag_2 = 8;
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

    private void getDataFromAPI(Label result_in_cu, int flag_1, int flag_2) throws IOException {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        URL url = new URL("http://api.exchangeratesapi.io/v1/latest?access_key=f7b094c94103b26551de9ac2e4bbd6e5&symbols=LKR,CHF,JPY,CAD,AUD,GBP,INR,USD&format=1");
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
            Double inr_rate = rates.getDouble("INR");
            Double usd_rate = rates.getDouble("USD");
            Double gbp_rate = rates.getDouble("GBP");
            Double jpy_rate = rates.getDouble("JPY");
            Double cad_rate = rates.getDouble("CAD");
            Double chf_rate = rates.getDouble("CHF");
            Double aud_rate = rates.getDouble("AUD");

            if ( flag_1 == 0){
                if ( flag_2 == 0){
                    result_in_cu.setText(decimalFormat.format(entered));
                }
                else if ( flag_2 == 1){
                    Double lkr_to_inr = ( entered * inr_rate)/lkr_rate;
                    result_in_cu.setText(decimalFormat.format(lkr_to_inr));
                }
                else if ( flag_2 == 2){
                    Double lkr_to_usd = ( entered * usd_rate)/lkr_rate;
                    result_in_cu.setText(decimalFormat.format(lkr_to_usd));
                }
                else if ( flag_2 == 3){
                    Double lkr_to_eur = lkr_rate * entered;
                    result_in_cu.setText(decimalFormat.format(lkr_to_eur));
                }
                else if ( flag_2 == 4){
                    Double lkr_to_gbp = ( entered * gbp_rate)/lkr_rate;
                    result_in_cu.setText(decimalFormat.format(lkr_to_gbp));
                }
                else if ( flag_2 == 5){
                    Double lkr_to_aud = ( entered * aud_rate)/lkr_rate;
                    result_in_cu.setText(decimalFormat.format(lkr_to_aud));
                }
                else if ( flag_2 == 6){
                    Double lkr_to_cad = ( entered * cad_rate)/lkr_rate;
                    result_in_cu.setText(decimalFormat.format(lkr_to_cad));
                }
                else if ( flag_2 == 7){
                    Double lkr_to_chf = ( entered * chf_rate)/lkr_rate;
                    result_in_cu.setText(decimalFormat.format(lkr_to_chf));
                }
                else if ( flag_2 == 8){
                    Double lkr_to_jpy = ( entered * jpy_rate)/lkr_rate;
                    result_in_cu.setText(decimalFormat.format(lkr_to_jpy));
                }
            }

            if ( flag_1 == 1){
                if ( flag_2 == 0){
                    Double inr_to_lkr = (entered * lkr_rate)/ inr_rate;
                    result_in_cu.setText(decimalFormat.format(inr_to_lkr));
                }
                else if ( flag_2 == 1){
                    result_in_cu.setText(decimalFormat.format(entered));
                }
                else if ( flag_2 == 2){
                    Double inr_to_usd = ( entered * usd_rate)/inr_rate;
                    result_in_cu.setText(decimalFormat.format(inr_to_usd));
                }
                else if ( flag_2 == 3){
                    Double inr_to_eur = inr_rate * entered;
                    result_in_cu.setText(decimalFormat.format(inr_to_eur));
                }
                else if ( flag_2 == 4){
                    Double inr_to_gbp = ( entered * gbp_rate)/inr_rate;
                    result_in_cu.setText(decimalFormat.format(inr_to_gbp));
                }
                else if ( flag_2 == 5){
                    Double inr_to_aud = ( entered * aud_rate)/inr_rate;
                    result_in_cu.setText(decimalFormat.format(inr_to_aud));
                }
                else if ( flag_2 == 6){
                    Double inr_to_cad = ( entered * cad_rate)/inr_rate;
                    result_in_cu.setText(decimalFormat.format(inr_to_cad));
                }
                else if ( flag_2 == 7){
                    Double inr_to_chf = ( entered * chf_rate)/inr_rate;
                    result_in_cu.setText(decimalFormat.format(inr_to_chf));
                }
                else if ( flag_2 == 8){
                    Double inr_to_jpy = ( entered * jpy_rate)/inr_rate;
                    result_in_cu.setText(decimalFormat.format(inr_to_jpy));
                }
            }

            if ( flag_1 == 2){
                if ( flag_2 == 0){
                    Double usd_to_lkr = (entered * lkr_rate)/ usd_rate;
                    result_in_cu.setText(decimalFormat.format(usd_to_lkr));
                }
                else if ( flag_2 == 1){
                    Double usd_to_inr = (entered * inr_rate)/ usd_rate;
                    result_in_cu.setText(decimalFormat.format(usd_to_inr));
                }
                else if ( flag_2 == 2){
                    result_in_cu.setText(decimalFormat.format(entered));
                }
                else if ( flag_2 == 3){
                    Double usd_to_eur = usd_rate * entered;
                    result_in_cu.setText(decimalFormat.format(usd_to_eur));
                }
                else if ( flag_2 == 4){
                    Double usd_to_gbp = ( entered * gbp_rate)/usd_rate;
                    result_in_cu.setText(decimalFormat.format(usd_to_gbp));
                }
                else if ( flag_2 == 5){
                    Double usd_to_aud = ( entered * aud_rate)/usd_rate;
                    result_in_cu.setText(decimalFormat.format(usd_to_aud));
                }
                else if ( flag_2 == 6){
                    Double usd_to_cad = ( entered * cad_rate)/usd_rate;
                    result_in_cu.setText(decimalFormat.format(usd_to_cad));
                }
                else if ( flag_2 == 7){
                    Double usd_to_chf = ( entered * chf_rate)/usd_rate;
                    result_in_cu.setText(decimalFormat.format(usd_to_chf));
                }
                else if ( flag_2 == 8){
                    Double usd_to_jpy = ( entered * jpy_rate)/usd_rate;
                    result_in_cu.setText(decimalFormat.format(usd_to_jpy));
                }
            }

            if ( flag_1 == 3){
                if ( flag_2 == 0){
                    Double eur_to_lkr = (entered * lkr_rate);
                    result_in_cu.setText(decimalFormat.format(eur_to_lkr));
                }
                else if ( flag_2 == 1){
                    Double eur_to_inr = (entered * inr_rate);
                    result_in_cu.setText(decimalFormat.format(eur_to_inr));
                }
                else if ( flag_2 == 2){
                    Double eur_to_usd = (entered * usd_rate);
                    result_in_cu.setText(decimalFormat.format(eur_to_usd));
                }
                else if ( flag_2 == 3){
                    Double eur_to_eur =  entered;
                    result_in_cu.setText(decimalFormat.format(eur_to_eur));
                }
                else if ( flag_2 == 4){
                    Double eur_to_gbp = ( entered * gbp_rate);
                    result_in_cu.setText(decimalFormat.format(eur_to_gbp));
                }
                else if ( flag_2 == 5){
                    Double eur_to_aud = ( entered * aud_rate);
                    result_in_cu.setText(decimalFormat.format(eur_to_aud));
                }
                else if ( flag_2 == 6){
                    Double eur_to_cad = ( entered * cad_rate);
                    result_in_cu.setText(decimalFormat.format(eur_to_cad));
                }
                else if ( flag_2 == 7){
                    Double eur_to_chf = ( entered * chf_rate);
                    result_in_cu.setText(decimalFormat.format(eur_to_chf));
                }
                else if ( flag_2 == 8){
                    Double eur_to_jpy = ( entered * jpy_rate);
                    result_in_cu.setText(decimalFormat.format(eur_to_jpy));
                }
            }

            if ( flag_1 == 4){
                if ( flag_2 == 0){
                    Double gbp_to_lkr = (entered * lkr_rate)/ gbp_rate;
                    result_in_cu.setText(decimalFormat.format(gbp_to_lkr));
                }
                else if ( flag_2 == 1){
                    Double gbp_to_inr = (entered * inr_rate)/ gbp_rate;
                    result_in_cu.setText(decimalFormat.format(gbp_to_inr));
                }
                else if ( flag_2 == 2){
                    Double gbp_to_usd = (entered * usd_rate)/ gbp_rate;
                    result_in_cu.setText(decimalFormat.format(gbp_to_usd));
                }
                else if ( flag_2 == 3){
                    Double gbp_to_eur = gbp_rate * entered;
                    result_in_cu.setText(decimalFormat.format(gbp_to_eur));
                }
                else if ( flag_2 == 4){
                    Double gbp_to_gbp = entered;
                    result_in_cu.setText(decimalFormat.format(gbp_to_gbp));
                }
                else if ( flag_2 == 5){
                    Double gbp_to_aud = ( entered * aud_rate)/gbp_rate;
                    result_in_cu.setText(decimalFormat.format(gbp_to_aud));
                }
                else if ( flag_2 == 6){
                    Double gbp_to_cad = ( entered * cad_rate)/gbp_rate;
                    result_in_cu.setText(decimalFormat.format(gbp_to_cad));
                }
                else if ( flag_2 == 7){
                    Double gbp_to_chf = ( entered * chf_rate)/gbp_rate;
                    result_in_cu.setText(decimalFormat.format(gbp_to_chf));
                }
                else if ( flag_2 == 8){
                    Double gbp_to_jpy = ( entered * jpy_rate)/gbp_rate;
                    result_in_cu.setText(decimalFormat.format(gbp_to_jpy));
                }
            }

            if ( flag_1 == 5){
                if ( flag_2 == 0){
                    Double aud_to_lkr = (entered * lkr_rate)/ aud_rate;
                    result_in_cu.setText(decimalFormat.format(aud_to_lkr));
                }
                else if ( flag_2 == 1){
                    Double aud_to_inr = (entered * inr_rate)/ aud_rate;
                    result_in_cu.setText(decimalFormat.format(aud_to_inr));
                }
                else if ( flag_2 == 2){
                    Double aud_to_usd = (entered * usd_rate)/ aud_rate;
                    result_in_cu.setText(decimalFormat.format(aud_to_usd));
                }
                else if ( flag_2 == 3){
                    Double aud_to_eur = aud_rate * entered;
                    result_in_cu.setText(decimalFormat.format(aud_to_eur));
                }
                else if ( flag_2 == 4){
                    Double aud_to_gbp = ( entered * gbp_rate)/ aud_rate;
                    result_in_cu.setText(decimalFormat.format(aud_to_gbp));
                }
                else if ( flag_2 == 5){
                    Double aud_to_aud = entered ;
                    result_in_cu.setText(decimalFormat.format(aud_to_aud));
                }
                else if ( flag_2 == 6){
                    Double aud_to_cad = ( entered * cad_rate)/ aud_rate;
                    result_in_cu.setText(decimalFormat.format(aud_to_cad));
                }
                else if ( flag_2 == 7){
                    Double aud_to_chf = ( entered * chf_rate)/ aud_rate;
                    result_in_cu.setText(decimalFormat.format(aud_to_chf));
                }
                else if ( flag_2 == 8){
                    Double aud_to_jpy = ( entered * jpy_rate)/aud_rate;
                    result_in_cu.setText(decimalFormat.format(aud_to_jpy));
                }
            }

            if ( flag_1 == 6){
                if ( flag_2 == 0){
                    Double cad_to_lkr = (entered * lkr_rate)/ cad_rate;
                    result_in_cu.setText(decimalFormat.format(cad_to_lkr));
                }
                else if ( flag_2 == 1){
                    Double cad_to_inr = (entered * inr_rate)/ cad_rate;
                    result_in_cu.setText(decimalFormat.format(cad_to_inr));
                }
                else if ( flag_2 == 2){
                    Double cad_to_usd = (entered * usd_rate)/ cad_rate;
                    result_in_cu.setText(decimalFormat.format(cad_to_usd));
                }
                else if ( flag_2 == 3){
                    Double cad_to_eur = cad_rate * entered;
                    result_in_cu.setText(decimalFormat.format(cad_to_eur));
                }
                else if ( flag_2 == 4){
                    Double cad_to_gbp = ( entered * gbp_rate)/ cad_rate;
                    result_in_cu.setText(decimalFormat.format(cad_to_gbp));
                }
                else if ( flag_2 == 5){
                    Double cad_to_aud = ( entered * aud_rate)/ cad_rate;
                    result_in_cu.setText(decimalFormat.format(cad_to_aud));
                }
                else if ( flag_2 == 6){
                    Double cad_to_cad = ( entered * cad_rate);
                    result_in_cu.setText(decimalFormat.format(cad_to_cad));
                }
                else if ( flag_2 == 7){
                    Double cad_to_chf = ( entered * chf_rate)/ cad_rate;
                    result_in_cu.setText(decimalFormat.format(cad_to_chf));
                }
                else if ( flag_2 == 8){
                    Double cad_to_jpy = ( entered * jpy_rate)/ cad_rate;
                    result_in_cu.setText(decimalFormat.format(cad_to_jpy));
                }
            }

            if ( flag_1 == 7){
                if ( flag_2 == 0){
                    Double chf_to_lkr = (entered * lkr_rate)/ chf_rate;
                    result_in_cu.setText(decimalFormat.format(chf_to_lkr));
                }
                else if ( flag_2 == 1){
                    Double chf_to_inr = (entered * inr_rate)/ chf_rate;
                    result_in_cu.setText(decimalFormat.format(chf_to_inr));
                }
                else if ( flag_2 == 2){
                    Double chf_to_usd = (entered * usd_rate)/ chf_rate;
                    result_in_cu.setText(decimalFormat.format(chf_to_usd));
                }
                else if ( flag_2 == 3){
                    Double chf_to_eur = chf_rate * entered;
                    result_in_cu.setText(decimalFormat.format(chf_to_eur));
                }
                else if ( flag_2 == 4){
                    Double chf_to_gbp = ( entered * gbp_rate)/ chf_rate;
                    result_in_cu.setText(decimalFormat.format(chf_to_gbp));
                }
                else if ( flag_2 == 5){
                    Double chf_to_aud = ( entered * aud_rate)/ chf_rate;
                    result_in_cu.setText(decimalFormat.format(chf_to_aud));
                }
                else if ( flag_2 == 6){
                    Double chf_to_cad = ( entered * cad_rate) / chf_rate;
                    result_in_cu.setText(decimalFormat.format(chf_to_cad));
                }
                else if ( flag_2 == 7){
                    Double chf_to_chf =  entered ;
                    result_in_cu.setText(decimalFormat.format(chf_to_chf));
                }
                else if ( flag_2 == 8){
                    Double chf_to_jpy = ( entered * jpy_rate)/ chf_rate;
                    result_in_cu.setText(decimalFormat.format(chf_to_jpy));
                }
            }

            if ( flag_1 == 8){
                if ( flag_2 == 0){
                    Double jpy_to_lkr = (entered * lkr_rate)/ jpy_rate;
                    result_in_cu.setText(decimalFormat.format(jpy_to_lkr));
                }
                else if ( flag_2 == 1){
                    Double jpy_to_inr = (entered * inr_rate)/ jpy_rate;
                    result_in_cu.setText(decimalFormat.format(jpy_to_inr));
                }
                else if ( flag_2 == 2){
                    Double jpy_to_usd = (entered * usd_rate)/ jpy_rate;
                    result_in_cu.setText(decimalFormat.format(jpy_to_usd));
                }
                else if ( flag_2 == 3){
                    Double jpy_to_eur = jpy_rate * entered;
                    result_in_cu.setText(decimalFormat.format(jpy_to_eur));
                }
                else if ( flag_2 == 4){
                    Double jpy_to_gbp = ( entered * gbp_rate)/ jpy_rate;
                    result_in_cu.setText(decimalFormat.format(jpy_to_gbp));
                }
                else if ( flag_2 == 5){
                    Double jpy_to_aud = ( entered * aud_rate)/ jpy_rate;
                    result_in_cu.setText(decimalFormat.format(jpy_to_aud));
                }
                else if ( flag_2 == 6){
                    Double jpy_to_cad = ( entered * cad_rate) / jpy_rate;
                    result_in_cu.setText(decimalFormat.format(jpy_to_cad));
                }
                else if ( flag_2 == 7){
                    Double jpy_to_chf =  ( entered * chf_rate) / jpy_rate ;
                    result_in_cu.setText(decimalFormat.format(jpy_to_chf));
                }
                else if ( flag_2 == 8){
                    Double jpy_to_jpy = entered;
                    result_in_cu.setText(decimalFormat.format(jpy_to_jpy));
                }
            }
        }
    }
}

package com.busreservationsystem.controllers;

import com.busreservationsystem.App;
import com.busreservationsystem.system.Client;
import com.busreservationsystem.system.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public abstract class ClientController {

    @FXML
    public Label usernameLabel;

    @FXML
    public Label fullNameLabel;

    @FXML
    public Label emailLabel;

    @FXML
    public Label balanceLabel;

    @FXML
    public Button gotoMakeBookings;

    @FXML
    public Button gotoViewBookings;

    @FXML
    public Button gotoEditProfile;

    @FXML
    public Button gotoManageBalance;


    public void switchForm(ActionEvent event) {
        // Switch scenes from buttons on the bottom left
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();
        switch (buttonId) {
            case "gotoMakeBookings" -> loadFXML("ClientMakeBookings");
            case "gotoViewBookings" -> loadFXML("ClientViewBookings");
            case "gotoEditProfile" -> loadFXML("ClientEditProfile");
            case "gotoManageBalance" -> loadFXML("ClientManageBalance");
        }
    }

    public void setCredentials() {
        Client client = Database.getCurrentClient();
        usernameLabel.setText(client.getUsername());
        fullNameLabel.setText(client.getFullName());
        emailLabel.setText(client.getEmail());
        balanceLabel.setText(String.format("%.2f$", client.getBalance()));
    }

    protected void loadFXML(String fxml) {
        // Load FXML File
        try {
            App.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

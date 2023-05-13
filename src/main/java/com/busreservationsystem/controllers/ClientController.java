package com.busreservationsystem.controllers;

import com.busreservationsystem.App;
import com.busreservationsystem.system.Client;
import com.busreservationsystem.system.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


/**
 * ClientController is the abstract superclass of all Controller classes for the Client view.
 * This controller handles common operations among all its ClientController subclasses.
 *
 */
public abstract class ClientController {

    @FXML
    public Label usernameLabel, fullNameLabel, emailLabel, balanceLabel;

    @FXML
    public Button gotoMakeBookings, gotoViewBookings, gotoEditProfile, gotoManageBalance;

    /**
     * This method is called when one of the buttons on the bottom left of the scene is clicked.
     * Handles the logic for switching between views.
     *
     * @param event The ActionEvent triggered by clicking one of the buttons.
     */
    public void switchForm(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();
        switch (buttonId) {
            case "gotoMakeBookings" -> loadFXML("ClientMakeBookings");
            case "gotoViewBookings" -> loadFXML("ClientViewBookings");
            case "gotoEditProfile" -> loadFXML("ClientEditProfile");
            case "gotoManageBalance" -> loadFXML("ClientManageBalance");
        }
    }

    /**
     * This method is called in the initialize() method of its subclasses.
     * Handles the logic loading the user data in the top left pane by retrieving the current client from the Database.
     */
    public void setCredentials() {
        Client client = Database.getCurrentClient();
        usernameLabel.setText(client.getUsername());
        fullNameLabel.setText(client.getFullName());
        emailLabel.setText(client.getEmail());
        balanceLabel.setText(String.format("%.2f$", client.getBalance()));
    }

    /**
     * This method loads an FXML file and sets it as the root for the current scene.
     * @param fxml The filename of the FXML file to load.
     */
    protected void loadFXML(String fxml) {
        // Load FXML File
        try {
            App.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

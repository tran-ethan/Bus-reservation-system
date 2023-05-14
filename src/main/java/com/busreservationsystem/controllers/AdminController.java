package com.busreservationsystem.controllers;

import com.busreservationsystem.App;
import com.busreservationsystem.system.Admin;
import com.busreservationsystem.system.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


/**
 * AdminController is the abstract superclass of all Controller classes for the Admin view.
 * This controller handles common operations among all its AdminController subclasses.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyhronopoulos
 */
public abstract class AdminController {

    @FXML
    public Label usernameLabel;

    @FXML
    public Label fullNameLabel;

    @FXML
    public Label emailLabel;

    @FXML
    public Button gotoManageBuses;

    @FXML
    public Button gotoManageClients;

    @FXML
    public Button gotoManageBookings;

    @FXML
    public Button gotoEditProfile;

    /**
     * This method triggered when one of the buttons on the bottom left of the scene is clicked.
     * Handles the logic for switching between views.
     * @param event The ActionEvent triggered by clicking one of the buttons.
     */
    public void switchForm(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();
        switch (buttonId) {
            case "gotoManageBuses" -> loadFXML("AdminManageBuses");
            case "gotoManageClients" -> loadFXML("AdminManageClients");
            case "gotoManageBookings" -> loadFXML("AdminManageBookings");
            case "gotoEditProfile" -> loadFXML("AdminEditProfile");
        }
    }

    /**
     * This method is called in the initialize() method of its subclasses.
     * Handles the logic loading the user data in the top left pane by retrieving the current client from the Database.
     */
    public void setCredentials() {
        Admin admin = Database.getCurrentAdmin();
        usernameLabel.setText(admin.getUsername());
        fullNameLabel.setText(admin.getFullName());
        emailLabel.setText(admin.getEmail());
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

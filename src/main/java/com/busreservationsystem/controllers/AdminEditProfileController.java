package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Admin;
import com.busreservationsystem.system.Client;
import com.busreservationsystem.system.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditProfileController extends AdminController implements Initializable {

    @FXML
    private Label oldUsername;

    @FXML
    private Label oldName;

    @FXML
    private Label oldEmail;

    @FXML
    private Label newUsername;

    @FXML
    private Label newName;

    @FXML
    private Label newEmail;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    private Admin admin;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        admin = Database.getCurrentAdmin();
        setCredentials();
        oldUsername.setText(admin.getUsername());
        oldName.setText(admin.getFullName());
        oldEmail.setText(admin.getEmail());
    }

    /**
     * Handles changing user credentials, handles appropriate error messages for user interaction.
     * Is called when "Save" button is clicked
     * @param event The source of the event.
     */
    @FXML
    void save(ActionEvent event) {
        // Update user credentials
        admin.setUsername(usernameField.getText());
        admin.setFullName(nameField.getText());
        admin.setEmail(emailField.getText());
        admin.setPassword(passwordField.getText());
        // Reloads page to update left panel
        loadFXML("AdminEditProfile");
    }

    /**
     * Handles the text change event for the text fields - is called everytime a character is inputted into a field.
     * Updates the corresponding labels based on the source of the field that has been changed.
     * @param event The source of the event.
     */
    @FXML
    void newCredentialsChange(KeyEvent event) {
        TextField field = (TextField) event.getSource();
        String fieldId = field.getId();
        String newValue = field.getText();
        switch (fieldId) {
            case "usernameField" -> newUsername.setText(newValue);
            case "nameField" -> newName.setText(newValue);
            case "emailField" -> newEmail.setText(newValue);
        }
    }
}

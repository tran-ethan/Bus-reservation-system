package com.busreservationsystem.controllers;

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

public class ClientEditProfileControllerClient extends ClientController implements Initializable {

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

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = Database.getCurrentClient();
        setCredentials();
        oldUsername.setText(client.getUsername());
        oldName.setText(client.getFullName());
        oldEmail.setText(client.getEmail());
    }

    /**
     * Handles changing user credentials, handles appropriate error messages for user interaction.
     * Is called when "Save" button is clicked
     * @param event The source of the event.
     */
    @FXML
    void save(ActionEvent event) {
        // Update user credentials
        client.setUsername(usernameField.getText());
        client.setFullName(nameField.getText());
        client.setEmail(emailField.getText());
        client.setPassword(passwordField.getText());
        // Reloads page to update left panel
        loadFXML("ClientEditProfile");
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

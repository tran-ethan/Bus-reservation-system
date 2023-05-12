package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Booking;
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
     * Updates all the bookings to link to the current Client.
     *
     * @param event The source of event: is called when "Save" button is clicked
     */
    @FXML
    void save(ActionEvent event) {
        String newUsername = usernameField.getText();
        String username = oldUsername.getText();

        // Update user credentials
        client.setUsername(newUsername);
        client.setFullName(nameField.getText());
        client.setEmail(emailField.getText());
        client.setPassword(passwordField.getText());

        // Change all bookings to match new user credentials
        for (Booking booking: Database.getBookings()) {
            if (booking.getClientUsername().equals(username)) {
                booking.setClientUsername(newUsername);
            }
        }

        // Reloads page to update left panel
        loadFXML("ClientEditProfile");
    }

    /**
     * Handles the text change event logic for the text fields.
     * Updates the corresponding labels based on the source of the field that has been changed.
     *
     * @param event The source of the event - is called everytime a character is inputted into a field.
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

package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Booking;
import com.busreservationsystem.system.Client;
import com.busreservationsystem.system.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Nikolaos Polyronopoulos
 * @author Christopher Soussa
 * @author Ethan Tran
 */
public class ClientEditProfileController extends ClientController implements Initializable {

    @FXML
    private Label oldUsername, oldName, oldEmail;

    @FXML
    private Label newUsername, newName, newEmail;

    @FXML
    private TextField usernameField, nameField;

    @FXML
    private TextField emailField, passwordField;

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
     * Updates all the bookings to link to the current Client's username.
     *
     * @param event The source of event: triggered when "Save" button is clicked
     */
    @FXML
    void save(ActionEvent event) {
        String newUsername = usernameField.getText();
        String newFullName = nameField.getText();
        String newEmail = emailField.getText();
        String username = oldUsername.getText();

        try {
            // Update user credentials
            client.setUsername(newUsername);
            client.setFullName(newFullName);
            client.setEmail(newEmail);
            client.setPassword(passwordField.getText());

            // Change all bookings to match new user credentials
            for (Booking booking: Database.getBookings()) {
                if (booking.getClientUsername().equals(username)) {
                    booking.setClientUsername(newUsername);
                }
            }

            // Display success
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Profile successfully edited.");
            alert.setContentText(String.format("""
                    NEW CREDENTIALS
                    - Username: %s
                    - Name: %s
                    - Email: %s""",
                    newUsername, newFullName, newEmail));
            alert.showAndWait();

        } catch (IllegalArgumentException e) {

            // Display alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            String methodName = e.getStackTrace()[0].getMethodName();
            alert.setTitle(e.getMessage());
            String error = switch (e.getMessage()) {
                case "Username is already taken" -> "Please choose another name. This one already exists.";
                case "Invalid email format" -> "Please enter a valid email address.";
                case "Invalid name" -> "Please enter your full name - must contain both first and last name";
                default -> "Please fill all fields before saving.";
            };
            alert.setContentText(error);

            /* If one field is invalid after the username, reset all credentials that have been set before that
            field back to previously valid state. Only call when method that throws exception is not setUsername
            because it will rethrow an exception by setting it back to previous name, which already exists */
            if (!methodName.equals("setUsername")) {
                client.setUsername(username);
                client.setFullName(oldName.getText());
                client.setEmail(oldEmail.getText());
            }
            alert.showAndWait();
        }

        // Reloads page to update left panel
        loadFXML("ClientEditProfile");
    }

    /**
     * Handles the text change event logic for the text fields.
     * Updates the corresponding labels based on the source of the field that has been changed.
     *
     * @param event The source of the event - triggered everytime a character is inputted into a field.
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

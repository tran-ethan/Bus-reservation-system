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
 * @author Ethan Tran
 * @author Nikolaos Polyronopoulos
 * @author Christopher Soussa
 */
public class AdminEditClientController extends AdminController implements Initializable {

    @FXML
    private TextField balanceField, emailField, nameField, passwordField, usernameField;

    @FXML
    private Label newBalance, newEmail, newName, newPassword, newUsername;

    @FXML
    private Label oldBalance, oldEmail, oldName, oldUsername, oldPassword;

    Client client = Database.getCurrentClient();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCredentials();
        oldUsername.setText(client.getUsername());
        oldName.setText(client.getFullName());
        oldEmail.setText(client.getEmail());
        oldBalance.setText(String.format("%.2f$", client.getBalance()));
        oldPassword.setText(client.getPassword());
    }

    /**
     * Handles changing client credentials, handles appropriate error messages for user interaction.
     *
     * @param event The source of the event - is called when "Save" button is clicked
     */
    @FXML
    void save(ActionEvent event) {
        String newUsername = usernameField.getText();
        String username = oldUsername.getText();

        try {
            // Update user credentials
            client.setUsername(usernameField.getText());
            client.setFullName(nameField.getText());
            client.setEmail(emailField.getText());
            client.setBalance(Double.parseDouble(balanceField.getText()));
            client.setPassword(passwordField.getText());

            // Update all bookings according to new username
            for (Booking booking : Database.getBookings()) {
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
                    newUsername, nameField.getText(), emailField.getText()));
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
        loadFXML("AdminManageClients");
    }

    /**
     * Handles the text change event for the text fields - is called everytime a character is inputted into a field.
     * Updates the corresponding labels based on the source of the field that has been changed.
     *
     * @param event The source of the event.
     */
    @FXML
    void newCredentialsChange(KeyEvent event) {
        TextField field = (TextField) event.getSource();
        String fieldId = field.getId();
        String newValue = field.getText();

        try {
            switch (fieldId) {
                case "usernameField" -> newUsername.setText(newValue);
                case "nameField" -> newName.setText(newValue);
                case "emailField" -> newEmail.setText(newValue);
                case "balanceField" -> newBalance.setText(String.format("%.2f$", Double.parseDouble(newValue)));
                case "passwordField" -> newPassword.setText(newValue);
            }
        } catch (NumberFormatException e) {
            newBalance.setText(String.format("%.2f$", 0.0));
        }
    }

    @FXML
    public void goBack(ActionEvent event) {
        loadFXML("AdminManageClients");
    }
}

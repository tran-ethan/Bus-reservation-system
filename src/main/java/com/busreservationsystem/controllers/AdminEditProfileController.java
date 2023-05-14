package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Admin;
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
 */
public class AdminEditProfileController extends AdminController implements Initializable {

    @FXML
    private Label oldUsername, oldName, oldEmail;

    @FXML
    private Label newUsername, newName, newEmail;

    @FXML
    private TextField usernameField, nameField;

    @FXML
    private TextField emailField, passwordField;

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
     *
     * @param event The source of the event - is called when "Save" button is clicked
     */
    @FXML
    void save(ActionEvent event) {
        String newUsername = usernameField.getText();
        String newFullName = nameField.getText();
        String newEmail = emailField.getText();
        String username = oldUsername.getText();

        try {
            // Update user credentials
            admin.setUsername(newUsername);
            admin.setFullName(newFullName);
            admin.setEmail(newEmail);
            admin.setPassword(passwordField.getText());

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
                admin.setUsername(username);
                admin.setFullName(oldName.getText());
                admin.setEmail(oldEmail.getText());
            }
            alert.showAndWait();
        }

        // Reloads page to update left panel
        loadFXML("AdminEditProfile");
    }

    /**
     * Handles the text change event for the text fields
     * Updates the corresponding labels based on the source of the field that has been changed.
     *
     * @param event Source of the event - is called everytime a character is inputted into a field.
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

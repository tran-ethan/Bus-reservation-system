package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Admin;
import com.busreservationsystem.system.Client;
import com.busreservationsystem.system.Database;
import com.busreservationsystem.system.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * AuthenticationController manages the sign-up and login form in a JavaFX application.
 * This controller is linked to LoginForm.fxml and SignUpForm.fxml.
 * This controller handles the logic for user authentication and account creation functionalities.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyronopoulos
 * @author Christopher Soussa
 */
public class AuthenticationController extends ClientController {

    @FXML
    private TextField usernameField, passwordField, emailField, nameField;


    /**
     * Handle the login button click event.Validates user input data, performs log in operation, and
     * shows appropriate messages or alerts on failure.
     * It will set the current user in the Database class in order for other controllers to be able to fetch
     * information regarding the user in other scenes.
     */
    @FXML
    void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        for (Client client: Database.getClients()) {
            if (username.equals(client.getUsername()) && password.equals(client.getPassword())) {
                Database.setCurrentClient(client);
                loadFXML("ClientMakeBookings");
                return;
            }
        }
        for (Admin admin: Database.getAdmins()) {
            if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
                Database.setCurrentAdmin(admin);
                loadFXML("AdminManageBuses");
                return;
            }
        }

        // Display login failed
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Unsuccessful login");
        alert.setContentText("Incorrect username or password.");
        alert.showAndWait();
    }

    /**
     * Handle the sign-up button click event.
     * Validates user input data, creates a new account, and performs sign up operation.
     * Shows appropriate messages or alerts on success or failure.
     */
    @FXML
    void signup() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String fullName = nameField.getText();
        User user;
        try {
            if (username.endsWith("@KEY_ADMIN")) {
                username = username.split("@KEY_ADMIN")[0];
                user = new Admin(fullName, username, password, email);
                Database.addAdmin((Admin) user);
            } else {
                user = new Client(fullName, username, password, email);
                Database.addClient((Client) user);
            }

            // Display success for account creation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(String.format("Successfully created a new %s account", user.getType()));
            alert.setContentText(String.format("""
                    ACCOUNT INFORMATION
                    - Username: %s
                    - Full name: %s
                    - Email: %s
                    - Account type: %s""",
                    user.getUsername(), user.getFullName(), user.getEmail(), user.getType()));
            alert.showAndWait();

            loadFXML("LoginForm");
        } catch (IllegalArgumentException e) {
            // Display alert for invalid information
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(e.getMessage());
            String error = switch (e.getMessage()) {
                case "Username is already taken" -> "Please choose another name. This one already exists.";
                case "Invalid email format" -> "Please enter a valid email address.";
                case "Invalid name" -> "Please enter your full name - must contain both first and last name";
                default -> "Please fill all fields before signing up.";
            };
            alert.setContentText(error);
            alert.showAndWait();
        }
    }

    @FXML
    @Override
    public void switchForm(ActionEvent event) {
        // Switch scenes from Login/Signup
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        loadFXML(buttonId.equals("gotoRegister") ? "SignUpForm" : "LoginForm");
    }

}
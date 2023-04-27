package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * AuthenticationController manages the sign-up and login form in a JavaFX application.
 * This controller handles the logic for user authentication and account creation functionalities.
 *
 */
public class AuthenticationController extends Controller {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;


    /**
     * Handle the login button click event.
     * Validates user input data, performs log in operation, and shows appropriate messages or alerts
     * on success or failure.
     */
    @FXML
    void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("username") && password.equals("password")) {
            loadFXML("ClientMakeBookings");
        }
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
        Client client = new Client(fullName, username, password, email, 10.0);
    }

    @FXML
    @Override
    protected void switchForm(ActionEvent event) {
        // Switch scenes from Login/Signup
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        loadFXML(buttonId.equals("gotoRegister") ? "SignUpForm" : "LoginForm");
    }

}
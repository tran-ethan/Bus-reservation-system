package com.busreservationsystem.controllers;

import com.busreservationsystem.App;
import com.busreservationsystem.system.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;


    @FXML
    protected void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("username") && password.equals("password")) {
            loadFXML("UserHome");
        }
    }

    @FXML
    protected void signup() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String fullName = nameField.getText();
        Client client = new Client(fullName, username, password, email, 10.0);
    }

    @FXML
    protected void switchForm(ActionEvent event) {
        // Switch scenes from Login/Signup
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        loadFXML(buttonId.equals("gotoRegister") ? "SignUpForm" : "LoginForm");
    }

    private void loadFXML(String fxml) {
        // Load FXML File
        try {
            App.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
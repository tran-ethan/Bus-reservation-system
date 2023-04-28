package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Client;
import com.busreservationsystem.system.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Client client = Database.getCurrentClient();
        setCredentials();
        oldUsername.setText(client.getUsername());
        oldName.setText(client.getFullName());
        oldEmail.setText(client.getEmail());
    }

    @FXML
    void save(ActionEvent event) {

    }
}

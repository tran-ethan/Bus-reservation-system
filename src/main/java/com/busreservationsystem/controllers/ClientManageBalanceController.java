package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Client;
import com.busreservationsystem.system.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientManageBalanceController extends ClientController implements Initializable {
    @FXML
    private Label afterBalance;

    @FXML
    private Label beforeBalance;

    @FXML
    private Button deposit;

    @FXML
    private Button withdraw;

    @FXML
    private TextField depositField;

    @FXML
    private TextField withdrawField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Client client = Database.getCurrentClient();
        setCredentials();
        beforeBalance.setText(String.format("%.2f$", client.getBalance()));
        afterBalance.setText(String.format("%.2f$", client.getBalance()));
    }

    @FXML
    void makeTransaction(ActionEvent event) {

    }
}

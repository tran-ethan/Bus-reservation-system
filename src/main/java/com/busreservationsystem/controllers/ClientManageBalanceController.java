package com.busreservationsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClientManageBalanceController extends ClientController {
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

    @FXML
    void makeTransaction(ActionEvent event) {

    }
}

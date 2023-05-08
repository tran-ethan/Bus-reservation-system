package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Client;
import com.busreservationsystem.system.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = Database.getCurrentClient();
        setCredentials();
        beforeBalance.setText(String.format("%.2f$", client.getBalance()));
        afterBalance.setText(String.format("%.2f$", client.getBalance()));
    }

    /**
     * Updates the displayed balance after transaction label based on amount entered in text field.
     * Handles appropriate errors if user interaction does not give a type of double.
     * @param event Source of the button event.
     */
    @FXML
    void newBalanceChange(KeyEvent event) {
        TextField field = (TextField) event.getSource();
        String fieldId = field.getId();
        double amount = Double.parseDouble(field.getText());
        double moneyAfterTransaction = client.getBalance();
        switch (fieldId) {
            case "depositField" -> moneyAfterTransaction = client.getBalance() + amount;
            case "withdrawField" -> moneyAfterTransaction = client.getBalance() - amount;
        }
        String formattedAmount = String.format("%.2f$", moneyAfterTransaction);
        afterBalance.setText(formattedAmount);
    }

    /**
     * Executes a transaction, either withdraw or deposit on client, based on the button clicked.
     * @param event Source of the event - Determines whether it is a withdrawal or deposit.
     */
    @FXML
    void makeTransaction(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonId = button.getId();
        switch (buttonId) {
            case "deposit" -> client.deposit(Double.parseDouble(depositField.getText()));
            case "withdraw" -> client.withdraw(Double.parseDouble(withdrawField.getText()));
        }
        loadFXML("ClientManageBalance");
    }
}

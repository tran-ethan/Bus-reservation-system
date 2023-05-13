package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Client;
import com.busreservationsystem.system.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientManageBalanceController extends ClientController implements Initializable {
    @FXML
    private Label afterBalance, beforeBalance;

    @FXML
    private TextField depositField, withdrawField;

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
     * Change after balance label if user does not give a type of double.
     *
     * @param event Source of the event - is called whenever a user types something in the Withdrawal or Deposit field.
     */
    @FXML
    void newBalanceChange(KeyEvent event) {
        TextField field = (TextField) event.getSource();
        String fieldId = field.getId();
        try {
            double amount = Double.parseDouble(field.getText());
            double moneyAfterTransaction = client.getBalance();
            switch (fieldId) {
                case "depositField" -> moneyAfterTransaction = client.getBalance() + amount;
                case "withdrawField" -> moneyAfterTransaction = client.getBalance() - amount;
            }
            String formattedAmount = String.format("%.2f$", moneyAfterTransaction);
            if (amount > 0) afterBalance.setText(formattedAmount);
        } catch (NumberFormatException e) {
            afterBalance.setText("Invalid number provided.");
        }
    }

    /**
     * Executes a transaction, either withdraw or deposit on client, based on the button clicked.
     * Handles appropriate errors if user interaction does not give a type of double.
     *
     * @param event Source of the event - Determines whether it is a withdrawal or deposit.
     */
    @FXML
    void makeTransaction(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonId = button.getId();
        try {
            switch (buttonId) {
                case "deposit" -> client.deposit(Double.parseDouble(depositField.getText()));
                case "withdraw" -> client.withdraw(Double.parseDouble(withdrawField.getText()));
            }
            // Display success
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successful Transaction");
            alert.setContentText(String.format("New balance: %.2f", client.getBalance()));
            alert.showAndWait();

            loadFXML("ClientManageBalance");
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Number " + e.getMessage());
            alert.setContentText("Please enter a valid number.");
            alert.showAndWait();
            depositField.clear();
            withdrawField.clear();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(e.getMessage());
            alert.setContentText("Please enter a positive number greater than zero.");
            alert.showAndWait();
            depositField.clear();
            withdrawField.clear();
        }
    }
}

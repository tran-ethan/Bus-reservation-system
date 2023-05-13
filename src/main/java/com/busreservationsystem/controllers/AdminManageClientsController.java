package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Booking;
import com.busreservationsystem.system.Bus;
import com.busreservationsystem.system.Client;
import com.busreservationsystem.system.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AdminManageClientsController extends AdminController implements Initializable {

    @FXML
    private TextField usernameField, fullNameField, emailField;

    @FXML
    private TableView<Client> table;

    @FXML
    private TableColumn<Client, Double> balanceCol;
    @FXML
    private TableColumn<Client, String> emailCol;
    @FXML
    private TableColumn<Client, String> fullNameCol;
    @FXML
    private TableColumn<Client, String> usernameCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCredentials();
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));

        ObservableList<Client> clients = FXCollections.observableArrayList(Database.getClients());
        table.setItems(clients);
    }

    /**
     * Performs filter operation on ObservableList of clients. Will only filter attributes that are provided.
     * If an attribute field is not provided, filter will ignore that field.
     * Will display on TableView buses whose fields match all the corresponding fields Username, full name, email.
     *
     * @param event Source of event: is called when Admin clicks on "Search" button.
     */
    @FXML
    void submitSearch(ActionEvent event) {
        String username = usernameField.getText();
        String name = fullNameField.getText();
        String email = emailField.getText();

        // Filter bus based on non-empty attributes
        ObservableList<Client> clients = FXCollections.observableArrayList();
        for (Client client: Database.getClients()) {
            if ((username.isEmpty() || client.getUsername().equals(username))
                    && (name.isEmpty()|| client.getFullName().equalsIgnoreCase(name))
                    && (email.isEmpty() || client.getEmail().equals(email))) {
                clients.add(client);
            }
        }

        table.setItems(clients);
    }

    /**
     * Handles logic for deleting a client.
     * Deletes all the bookings that the user currently possesses.
     *
     * @param event Source of event: is called when Admin clicks on "Delete"
     */
    @FXML
    void deleteClient(ActionEvent event) {
        try {
            Client client = table.getSelectionModel().getSelectedItem();
            Database.removeClient(client);
            String clientUsername = client.getUsername();
            Database.getBookings().removeIf(booking -> booking.getClientUsername().equals(clientUsername));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleted client successfully");
            alert.setContentText(String.format("Client '%s' has been deleted.", clientUsername));

            alert.showAndWait();
            loadFXML("AdminManageClients");
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(e.getMessage());
            alert.setContentText("Please select a client to delete.");

            alert.showAndWait();
        }

    }

    /**
     * Handles switching page to corresponding editing page for the selected Client.
     *
     * @param event Source of event: is called when Admin clicks on "Edit"
     */
    @FXML
    void editClient(ActionEvent event) {
        try {
            Client client = table.getSelectionModel().getSelectedItem();
            Database.setCurrentClient(client);
            loadFXML("AdminEditClient");
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(e.getMessage());
            alert.setContentText("Please select a client to edit.");

            alert.showAndWait();
        }
    }
}

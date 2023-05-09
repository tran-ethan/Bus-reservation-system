package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Admin;
import com.busreservationsystem.system.Client;
import com.busreservationsystem.system.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminManageClientsController extends AdminController implements Initializable {

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

    @FXML
    private Button edit, cancel;


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

    @FXML
    void deleteClient() {
        System.out.println("deleting client");
        Client client = table.getSelectionModel().getSelectedItem();
        Database.removeClient(client);
        loadFXML("AdminManageClients");
    }

    @FXML
    void editClient(ActionEvent event) {

    }
}

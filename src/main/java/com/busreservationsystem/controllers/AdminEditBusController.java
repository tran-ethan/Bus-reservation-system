package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Bus;
import com.busreservationsystem.system.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditBusController extends AdminController implements Initializable {

    @FXML
    private Label busIdLabel, dateLabel, departureLabel, destinationLabel, priceLabel;

    private final Bus bus = Database.getCurrentBus();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        busIdLabel.setText(bus.getId());
        departureLabel.setText(bus.getOrigin());
        destinationLabel.setText(bus.getDestination());
        dateLabel.setText(String.valueOf(bus.getDepartureDateValue()));
        priceLabel.setText(String.format("%.2f$", bus.getTicketPrice()));
    }

    @FXML
    public void save(ActionEvent event) {
        System.out.println("saving");
    }
    @FXML
    public void goBack(ActionEvent event) {
        loadFXML("AdminManageBuses");
    }
}

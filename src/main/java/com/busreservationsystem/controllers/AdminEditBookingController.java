package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Booking;
import com.busreservationsystem.system.Bus;
import com.busreservationsystem.system.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditBookingController extends AdminController implements Initializable {

    @FXML
    private TextField busIdField, columnField, rowField;

    @FXML
    private Label newBusId, newColumn, newRow;

    @FXML
    private Label oldBusId, oldColumn, oldRow;

    private final Booking booking = Database.getCurrentBooking();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCredentials();
        oldBusId.setText(booking.getBusId());
        oldRow.setText(String.valueOf(booking.getRow()));
        oldColumn.setText(String.valueOf(booking.getColumn()));
    }

    @FXML
    void goBack(ActionEvent event) {
        loadFXML("AdminManageBookings");
    }

    @FXML
    void newCredentialsChange(KeyEvent event) {
        TextField field = (TextField) event.getSource();
        String fieldId = field.getId();
        String newValue = field.getText();

        switch (fieldId) {
            case "busIdField" -> newBusId.setText(newValue);
            case "rowField" -> newRow.setText(newValue);
            case "columnField" -> newColumn.setText(newValue);
        }
    }

    @FXML
    void save(ActionEvent event) {
        String busId = busIdField.getText();
        Bus busFromId = Database.getBusFromId(busId);
        char row = rowField.getText().charAt(0);
        int col = Integer.parseInt(columnField.getText());
        booking.setBusId(busId);
        booking.setPrice(busFromId.getTicketPrice());
        booking.setOrigin(busFromId.getOrigin());
        booking.setDestination(busFromId.getDestination());
        booking.setDepartureDate(busFromId.getDepartureDateValue());
        booking.setDepartureTime(busFromId.getDepartureTimeValue());
        booking.setRow(row);
        booking.setColumn(col);
        loadFXML("AdminManageBookings");
    }

}

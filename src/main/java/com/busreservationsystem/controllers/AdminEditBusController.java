package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Bus;
import com.busreservationsystem.system.Database;
import com.busreservationsystem.system.Status;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AdminEditBusController extends AdminController implements Initializable {

    @FXML
    private Label busIdLabel, dateLabel, originLabel, destinationLabel, priceLabel;
    @FXML
    private TextField arrivalTimeField, busIdField, departureTimeField, destinationField, originField, ticketPriceField;
    @FXML
    private DatePicker departureDateField;
    @FXML
    private ChoiceBox<Status> statusField;

    private final Bus bus = Database.getCurrentBus();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // Set left pane labels
        busIdLabel.setText(bus.getId());
        originLabel.setText(bus.getOrigin());
        destinationLabel.setText(bus.getDestination());
        dateLabel.setText(bus.getDepartureDateValue().format(dateFormatter));
        priceLabel.setText(String.format("%.2f$", bus.getTicketPrice()));

        // Populate bus data fields
        departureDateField.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, dateFormatter) : null;
            }
        });
        departureDateField.setValue(bus.getDepartureDateValue());
        statusField.setItems(FXCollections.observableArrayList(Status.values()));
        statusField.setValue(bus.getStatusValue());
    }

    @FXML
    public void save(ActionEvent event) {
        String busId = busIdField.getText();
        String origin = originField.getText();
        String destination = destinationField.getText();
        double ticketPrice = (ticketPriceField.getText().isEmpty())
                           ? bus.getTicketPrice()
                           : Double.parseDouble(ticketPriceField.getText());
        LocalDate departureDate = departureDateField.getValue();
        LocalTime departureTime = (departureTimeField.getText().isEmpty())
                            ? bus.getDepartureTimeValue()
                            : LocalTime.parse(departureTimeField.getText(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime arrivalTime = (arrivalTimeField.getText().isEmpty())
                            ? bus.getArrivalTimeValue()
                            : LocalTime.parse(arrivalTimeField.getText(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        Status status = statusField.getValue();
        if (!busId.isEmpty()) bus.setId(busId);
        if (!origin.isEmpty()) bus.setOrigin(origin);
        if (!destination.isEmpty()) bus.setDestination(destination);
        bus.setTicketPrice(ticketPrice);
        bus.setDepartureDate(departureDate);
        bus.setDepartureTime(departureTime);
        bus.setArrivalTime(arrivalTime);
        bus.setStatus(status);
        loadFXML("AdminManageBuses");
    }

    @FXML
    public void goBack(ActionEvent event) {
        loadFXML("AdminManageBuses");
    }
}

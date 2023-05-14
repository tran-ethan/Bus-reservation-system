package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Booking;
import com.busreservationsystem.system.Bus;
import com.busreservationsystem.system.Database;
import com.busreservationsystem.system.Status;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;


/**
 * @author Ethan Tran
 */
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

    /**
     * Saves the changes made to a bus by updating its properties with the values entered in the corresponding fields.
     * If a field is empty, it will not change it and use the previous value.
     *
     * @param event Source of event: triggered when Admin clicks on the "Save" button
     */
    @FXML
    public void save(ActionEvent event) {
        String busId = busIdField.getText();
        String origin = originField.getText();
        String destination = destinationField.getText();
        try {
            // ParseDouble and LocalTime parse can possibly throw exceptions that will be caught
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

            // Only set values that are not empty. Non-empty values do not throw an exception, they will
            // be handled as if they remained unchanged
            Status status = statusField.getValue();
            if (!busId.isEmpty()) bus.setId(busId);
            if (!origin.isEmpty()) bus.setOrigin(origin);
            if (!destination.isEmpty()) bus.setDestination(destination);
            bus.setTicketPrice(ticketPrice);
            bus.setDepartureDate(departureDate);
            bus.setDepartureTime(departureTime);
            bus.setArrivalTime(arrivalTime);
            bus.setStatus(status);

            // Change all bookings to match new Bus ID
            for (Booking booking: Database.getBookings()) {
                // Matching bus only if ID is same as old ID
                if (booking.getBusId().equals(busIdLabel.getText())) {
                    if (!busId.isEmpty()) {
                        booking.setBusId(busId);
                    }
                    if (!origin.isEmpty() && booking.getOrigin().equals(originLabel.getText())) {
                        booking.setOrigin(origin);
                    }
                    if (!destination.isEmpty() && booking.getDestination().equals(destinationLabel.getText())) {
                        booking.setOrigin(destination);
                    }
                    booking.setPrice(ticketPrice);
                    System.out.println(departureDate);
                    booking.setDepartureDate(departureDate);
                    booking.setDepartureTime(departureTime);
                }
            }

            // Display successful edit
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bus successfully edited.");
            alert.setContentText(String.format("""
                    NEW INFORMATION
                    - Bus ID: %s
                    - Origin: %s
                    - Destination: %s
                    - Ticket price: %.2f$
                    - Departure date: %tY/%tm/%td
                    - Status: %s""",
                    busId, origin, destination, ticketPrice, departureDate, departureDate, departureDate, status));
            alert.showAndWait();

            loadFXML("AdminManageBuses");
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Number " + e.getMessage());
            alert.setContentText("Please enter a valid number for the ticket price.");
            ticketPriceField.clear();
            alert.showAndWait();
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid time or date format");
            alert.setContentText("Please enter a valid time format for the departure and arrival time/date.");
            departureTimeField.clear();
            arrivalTimeField.clear();
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(e.getMessage());
            String error = switch (e.getMessage()) {
                case "Bus ID already exists" -> "Please select another Bus ID.";
                case "Invalid ticket price" -> "Please select a ticket price greater than zero.";
                default -> "";
            };
            alert.setContentText(error);
            alert.showAndWait();
        }
    }

    @FXML
    public void goBack(ActionEvent event) {
        loadFXML("AdminManageBuses");
    }
}

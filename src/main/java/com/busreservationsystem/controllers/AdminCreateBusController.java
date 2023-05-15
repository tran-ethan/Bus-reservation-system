package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Bus;
import com.busreservationsystem.system.Database;
import com.busreservationsystem.system.Status;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;


/**
 * @author Ethan Tran
 * @author Nikolaos Polyronopoulos
 */
public class AdminCreateBusController extends AdminController implements Initializable {

    @FXML
    private Label busIdLabel, dateLabel, originLabel, destinationLabel, priceLabel;

    @FXML
    private TextField busIdField, destinationField, originField, ticketPriceField;

    @FXML
    private TextField arrivalTimeField, departureTimeField;

    @FXML
    private DatePicker departureDateField;

    @FXML
    private ChoiceBox<Status> statusField;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        departureDateField.setValue(LocalDate.now());
        dateLabel.setText(LocalDate.now().format(dateFormatter));
        statusField.setItems(FXCollections.observableArrayList(Status.values()));
        statusField.setValue(Status.ON_TIME);
    }

    /**
     * Creates a new bus by with updated properties entered in the corresponding fields.
     * Shows appropriate success and errors when fields do not have valid values or are empty.
     *
     * @param event Source of event: triggered when Admin clicks on the "Save" button
     */
    @FXML
    void save(ActionEvent event) {
        try {
            Bus bus = new Bus(
                    busIdField.getText(),
                    Double.parseDouble(ticketPriceField.getText()),
                    originField.getText(),
                    destinationField.getText(),
                    departureDateField.getValue(),
                    LocalTime.parse(departureTimeField.getText(), DateTimeFormatter.ofPattern("HH:mm:ss")),
                    LocalTime.parse(arrivalTimeField.getText(), DateTimeFormatter.ofPattern("HH:mm:ss")),
                    statusField.getValue()
            );
            Database.addBus(bus);

            // Display success
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
                    bus.getId(), bus.getOrigin(), bus.getDestination(), bus.getTicketPrice(),
                    bus.getDepartureDateValue(), bus.getDepartureDateValue(), bus.getDepartureDateValue(),
                    bus.getStatusValue()));

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
            alert.setTitle("Invalid time format");
            alert.setContentText("Please enter a valid time format for the departure and arrival time.");
            departureTimeField.clear();
            arrivalTimeField.clear();
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(e.getMessage());
            String error = switch (e.getMessage()) {
                case "Bus ID already exists" -> "Please select another Bus ID.";
                case "Invalid ticket price" -> "Please select a ticket price greater than zero.";
                default -> "Please enter all fields.";
            };
            alert.setContentText(error);
            alert.showAndWait();
        }
    }

    /**
     * Handles the date change event for the date.
     * Updates the date label on the left pane.
     *
     * @param event Source of the event: triggered everytime the date is updated.
     */
    @FXML
    void dateChange(ActionEvent event) {
        try {
            dateLabel.setText(departureDateField.getValue().format(dateFormatter));
        } catch (DateTimeParseException e) {
            dateLabel.setText("Invalid date format provided");
        }
    }

    /**
     * Handles the text change event for the text fields.
     * Updates the corresponding labels based on the source of the field that has been changed.
     *
     * @param event Source of the event: triggered everytime a character is inputted into a field.
     */
    @FXML
    void onTextChange(KeyEvent event) {
        TextField field = (TextField) event.getSource();
        String fieldId = field.getId();
        String newValue = field.getText();
        try {
            switch (fieldId) {
                case "busIdField" -> busIdLabel.setText(newValue);
                case "originField" -> originLabel.setText(newValue);
                case "destinationField" -> destinationLabel.setText(newValue);
                case "ticketPriceField" -> priceLabel.setText(String.format("%.2f$", Double.parseDouble(newValue)));
            }
        } catch (NumberFormatException e) {
            priceLabel.setText("Invalid price number.");
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        loadFXML("AdminManageBuses");
    }
}

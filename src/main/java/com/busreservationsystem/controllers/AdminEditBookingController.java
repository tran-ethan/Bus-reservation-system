package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Booking;
import com.busreservationsystem.system.Bus;
import com.busreservationsystem.system.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;


/**
 * @author Ethan Tran
 * @author Nikolaos Polyronopoulos
 */
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

    /**
     * Handles changing the new details for the labels.
     *
     * @param event Source of event - triggered everytime Admin enters a character into a field.
     */
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

    /**
     * Handles the save action event for editing a booking in the admin interface.
     * Will display appropriate error messages if fields are empty, bus does not exist,
     * invalid rows and columns, or seat has already been reserved.
     *
     * @param event Source of event - triggered when Admin clicks on "Save" button.
     */
    @FXML
    void save(ActionEvent event) {
        try {
            // Retrieve and check for valid rows and existing bus ID
            Bus busFromId = Database.getBusFromId(busIdField.getText());
            if (rowField.getText().length() != 1) throw new IllegalArgumentException("Invalid row");
            char row = Character.toUpperCase(rowField.getText().charAt(0));
            int col = Integer.parseInt(columnField.getText());

            // Check if seat is already taken
            if (busFromId.getSeats()[row - 'A'][col - 1]) throw new IllegalArgumentException("Seat already booked");
            busFromId.getSeats()[row - 'A'][col - 1] = true;

            // Set all attributes - possibly throws exceptions
            booking.setBusId(busIdField.getText());
            booking.setPrice(busFromId.getTicketPrice());
            booking.setOrigin(busFromId.getOrigin());
            booking.setDestination(busFromId.getDestination());
            booking.setDepartureDate(busFromId.getDepartureDateValue());
            booking.setDepartureTime(busFromId.getDepartureTimeValue());
            booking.setRow(row);
            booking.setColumn(col);

            // Display success
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bus successfully edited.");
            alert.setContentText(String.format("""
                    NEW BOOKING INFORMATION
                    - Bus ID: %s
                    - Ticket price: %.2f$
                    - Seat row: %c
                    - Seat column: %d""",
                    busFromId.getId(), busFromId.getTicketPrice(), row, col));
            alert.showAndWait();

            loadFXML("AdminManageBookings");
        } catch (NoSuchElementException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(e.getMessage());
            alert.setContentText("Please enter a bus ID that matches an existing bus.");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(e.getMessage());
            alert.setContentText("Please enter a valid seat column - it must be an integer between 1 and 4.");
            alert.showAndWait();
        } catch (ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid seat row or column");
            alert.setContentText("The row must be a single capital letter from A to J.\n" +
                    "The column must be an integer between 1 and 4.");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(e.getMessage());
            String error = switch (e.getMessage()) {
                case "Invalid row" -> "The row must be a single capital letter from A to J.";
                case "Invalid column" -> "Please enter a valid seat column - it must be an integer between 1 and 4.";
                case "Seat already booked" -> "Please enter another seat that is not already taken.";
                default -> "Please enter all fields.";
            };
            alert.setContentText(error);
            alert.showAndWait();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        loadFXML("AdminManageBookings");
    }
}

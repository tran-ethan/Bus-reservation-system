package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Booking;
import com.busreservationsystem.system.Bus;
import com.busreservationsystem.system.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


/**
 * @author Ethan Tran
 */
public class ClientViewBookingsController extends ClientController implements Initializable {


    @FXML
    private TableView<Booking> table;

    @FXML
    private TableColumn<Booking, LocalDate> dateCol;

    @FXML
    private TableColumn<Booking, LocalTime> departureCol;

    @FXML
    private TableColumn<Booking, String> destinationCol, busIdCol, originCol;

    @FXML
    private TableColumn<Booking, Double> priceCol;

    @FXML
    private TableColumn<Booking, Character> rowCol;

    @FXML
    private TableColumn<Booking, Integer> colCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCredentials();
        // Set Cell Factory values to match
        busIdCol.setCellValueFactory(new PropertyValueFactory<>("busId"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        originCol.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        departureCol.setCellValueFactory(cellData -> cellData.getValue().getDepartureTime());
        departureCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                }
            }
        });
        dateCol.setCellValueFactory(cellData -> cellData.getValue().getDepartureDate());
        dateCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                }
            }
        });
        rowCol.setCellValueFactory(new PropertyValueFactory<>("row"));
        colCol.setCellValueFactory(new PropertyValueFactory<>("column"));

        // Set table values according to database. Only display bookings of the current user
        ObservableList<Booking> bookings = FXCollections.observableArrayList(Database.getCurrentClientBookings());
        table.setItems(bookings);
    }

    /**
     * Handles the logic for a user cancelling a booking.
     * Opens bus seating for cancelled seat and refunds money to the Client.
     *
     * @param event Source of event. triggered when user clicks on "Cancel" button.
     */
    @FXML
    void cancelBooking(ActionEvent event) {
        try {
            // Select booking - possiblt throws NullPointerException
            Booking booking = table.getSelectionModel().getSelectedItem();
            Database.removeBooking(booking);

            // Remove seat from corresponding bus
            Bus bus = Database.getBusFromId(booking.getBusId());
            int row = booking.getRow() - 'A';
            int col = booking.getColumn() - 1;
            bus.getSeats()[row][col] = false;

            // Refund client money
            Database.getCurrentClient().deposit(booking.getPrice());
            loadFXML("ClientViewBookings");

            // Display success
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Booking successfully deleted.");
            alert.setContentText(String.format("""
                    Booking information
                    - Bus ID: %s
                    - Ticket price (refunded): %.2f$
                    - Seat: %c%d""",
                    booking.getBusId(), booking.getPrice(), booking.getRow(), booking.getColumn()));
            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(e.getMessage());
            alert.setContentText("Please select a booking to cancel.");
            alert.showAndWait();
        }
    }
}

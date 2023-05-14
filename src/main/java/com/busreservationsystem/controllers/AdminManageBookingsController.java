package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Booking;
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
 * @author Nikolaos Polyhronopoulos
 */
public class AdminManageBookingsController extends AdminController implements Initializable {

    @FXML
    private TableView<Booking> table;

    @FXML
    private TableColumn<Booking, LocalDate> dateCol;

    @FXML
    private TableColumn<Booking, LocalTime> departureCol;

    @FXML
    private TableColumn<Booking, String> usernameCol, destinationCol, busIdCol, originCol;

    @FXML
    private TableColumn<Booking, Double> priceCol;

    @FXML
    private TableColumn<Booking, Character> rowCol;

    @FXML
    private TableColumn<Booking, Integer> colCol;

    @FXML
    private TextField usernameField, busIdField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCredentials();
        // Set Cell Factory values to match
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("clientUsername"));
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
                    setText(item.format(DateTimeFormatter.ofPattern("hh:mm:ss")));
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

        // Set table values according to database
        ObservableList<Booking> bookings = FXCollections.observableArrayList(Database.getBookings());
        table.setItems(bookings);
    }

    /**
     * Performs filter operation on ObservableList of bookings. Will only filter attributes that are provided.
     * If an attribute field is not provided, filter will ignore that field. Will filter based
     * on Client username and bus ID.
     *
     * @param event Source of event: triggered when Admin clicks on "Search" button.
     */
    @FXML
    void submitSearch(ActionEvent event) {
        String username = usernameField.getText();
        String busId = busIdField.getText();

        // Filter bus based on non-empty attributes
        ObservableList<Booking> bookings = FXCollections.observableArrayList();
        for (Booking booking: Database.getBookings()) {
            if ((booking.getClientUsername().equals(username) || username.isEmpty())
            && (booking.getBusId().equals(busId) || busId.isEmpty())) {
                bookings.add(booking);
            }
        }

        table.setItems(bookings);
    }

    /**
     * Handles switching page to corresponding editing page for the selected Booking.
     *
     * @param event Source of event: triggered when Admin clicks on "Edit"
     */
    @FXML
    void editBooking(ActionEvent event) {
        try {
            Booking booking = table.getSelectionModel().getSelectedItem();
            Database.setCurrentBooking(booking);
            loadFXML("AdminEditBooking");
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(e.getMessage());
            alert.setContentText("Please select a booking to edit.");
            alert.showAndWait();
        }
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
            Booking booking = table.getSelectionModel().getSelectedItem();
            // Get row, col, as int that is 0-indexed
            Database.removeBooking(booking);
            int row = booking.getRow() - 'A';
            int col = booking.getColumn() - 1;
            Database.getBusFromId(booking.getBusId()).getSeats()[row][col] = false;
            Database.getClientFromUsername(booking.getClientUsername()).deposit(booking.getPrice());
            loadFXML("AdminManageBookings");
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(e.getMessage());
            alert.setContentText("Please select a booking to delete.");
            alert.showAndWait();
        }
    }
}

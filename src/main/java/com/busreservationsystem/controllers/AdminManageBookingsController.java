package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Admin;
import com.busreservationsystem.system.Booking;
import com.busreservationsystem.system.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AdminManageBookingsController extends AdminController implements Initializable {

    @FXML
    private TableView<Booking> table;
    @FXML
    private TableColumn<Booking, String> usernameCol;
    @FXML
    private TableColumn<Booking, LocalTime> arrivalCol;

    @FXML
    private TableColumn<Booking, LocalDate> dateCol;

    @FXML
    private TableColumn<Booking, LocalTime> departureCol;

    @FXML
    private TableColumn<Booking, String> destinationCol;

    @FXML
    private TableColumn<Booking, String> busIdCol;

    @FXML
    private TableColumn<Booking, String> originCol;

    @FXML
    private TableColumn<Booking, Double> priceCol;

    @FXML
    private TableColumn<Booking, Character> rowCol;
    @FXML
    private TableColumn<Booking, Integer> colCol;
    @FXML
    private Button edit, cancel;

    private Admin admin;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        admin = Database.getCurrentAdmin();
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

    @FXML
    void editBooking(ActionEvent event) {
        Booking booking = table.getSelectionModel().getSelectedItem();
        Database.setCurrentBooking(booking);
        loadFXML("AdminEditBooking");
    }

    /**
     * Handles the logic for a user cancelling a booking.
     * Opens bus seating for cancelled seat and refunds money to the Client.
     *
     * @param event Source of event. Is called when user clicks on Cancel button.
     */
    @FXML
    void cancelBooking(ActionEvent event) {
        Booking booking = table.getSelectionModel().getSelectedItem();
        // Get row, col, as int that is 0-indexed
        int row = booking.getRow() - 'A';
        int col = booking.getColumn() - 1;
        Database.getBusFromId(booking.getBusId()).getSeats()[row][col] = false;
        Database.removeBooking(booking);
        Database.getClientFromUsername(booking.getClientUsername()).deposit(booking.getPrice());
        loadFXML("AdminManageBookings");
    }
}

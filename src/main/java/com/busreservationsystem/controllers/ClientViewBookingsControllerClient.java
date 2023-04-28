package com.busreservationsystem.controllers;

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

public class ClientViewBookingsControllerClient extends ClientMakeBookingsController implements Initializable {

    @FXML
    private TableView<Booking> table;
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
    private Button edit;

    @FXML
    private Button cancel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    }

    @FXML
    void cancelBooking(ActionEvent event) {

    }
}

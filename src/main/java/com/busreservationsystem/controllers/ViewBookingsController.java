package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Booking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ViewBookingsController extends MakeBookingsController implements Initializable {

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

    }
    @FXML
    void editBooking(ActionEvent event) {

    }

    @FXML
    void cancelBooking(ActionEvent event) {

    }
}

package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Bus;
import com.busreservationsystem.system.Database;
import com.busreservationsystem.system.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * MakeBookingsController is responsible for managing the booking process for the Bus Reservation System for the Client.
 * This controller initializes the bus table view with data from the database, displays bus information
 * including ID, origin, destination, departure time, arrival time, departure date, status, and ticket price.
 * Clients are able to search for buses and sort them according to specified fields.
 * This ability will allow clients to book a seat on the bus.
 */
public class ClientMakeBookingsController extends ClientController implements Initializable {

    @FXML
    private TableColumn<Bus, LocalTime> arrivalCol;

    @FXML
    private TableColumn<Bus, LocalDate> dateCol;

    @FXML
    private TextField dateField;

    @FXML
    protected ChoiceBox<String> dateSortField;

    @FXML
    private TableColumn<Bus, LocalTime> departureCol;

    @FXML
    private TextField departureField;

    @FXML
    private TableColumn<Bus, String> destinationCol;

    @FXML
    private TextField destinationField;

    @FXML
    private TableColumn<Bus, String> idCol;

    @FXML
    private TextField idField;

    @FXML
    private TableColumn<Bus, String> originCol;

    @FXML
    protected ChoiceBox<String> priceSortField;

    @FXML
    private TableColumn<Bus, Status> statusCol;

    @FXML
    private Button submit;

    @FXML
    private Button book;

    @FXML
    private TableView<Bus> table;

    @FXML
    private TableColumn<Bus, Integer> ticketPriceCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCredentials();
        // Set Cell Factory values to match
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ticketPriceCol.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        originCol.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        departureCol.setCellValueFactory(cellData -> cellData.getValue().getDepartureTime());
        departureCol.setCellFactory(new TimeCellFactory());
        arrivalCol.setCellValueFactory(cellData -> cellData.getValue().getArrivalTime());
        arrivalCol.setCellFactory(new TimeCellFactory());
        dateCol.setCellValueFactory(cellData -> cellData.getValue().getDepartureDate());
        dateCol.setCellFactory(new DateCellFactory());
        statusCol.setCellValueFactory(cellData -> cellData.getValue().getStatus());
        // Set table values according to database
        ObservableList<Bus> buses = FXCollections.observableArrayList(Database.getBuses());
        table.setItems(buses);
    }

    /**
     * Custom cell factory for the departure column in a JavaFX TableView.
     * Implements the Callback interface to provide a custom TableCell
     * class for displaying LocalTime values in the departure and
     * arrival columns according to a Time format of 'hh:mm:ss'.
     */
    private static class TimeCellFactory implements Callback<TableColumn<Bus, LocalTime>, TableCell<Bus, LocalTime>> {

        @Override
        public TableCell<Bus, LocalTime> call(TableColumn<Bus, LocalTime> col) {
            return new TimeTableCell();
        }

        // Custom TableCell implementation for departure column
        private static class TimeTableCell extends TableCell<Bus, LocalTime> {
            @Override
            public void updateItem(LocalTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format(item.format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                }
            }
        }
    }

    /**
     * Custom cell factory for the departure column in a JavaFX TableView.
     * Implements the Callback interface to provide a custom TableCell
     * for displaying LocalDate values in the departure columns
     * according to a Date format of 'yyyy/MM/dd'.
     */
    private static class DateCellFactory implements Callback<TableColumn<Bus, LocalDate>, TableCell<Bus, LocalDate>> {

        @Override
        public TableCell<Bus, LocalDate> call(TableColumn<Bus, LocalDate> col) {
            return new DepartureTableCell();
        }

        private static class DepartureTableCell extends TableCell<Bus, LocalDate> {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format(item.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
                }
            }
        }
    }

    @FXML
    private void submitSearch(ActionEvent event) {

    }

    @FXML
    private void makeBooking(ActionEvent event) {
        Bus bus = table.getSelectionModel().getSelectedItem();
        Database.setCurrentBus(bus);
        loadFXML("ClientSeatSelection");
    }
}

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
 *
 * @author Ethan Tran
 */
public class AdminManageBusesController extends AdminController implements Initializable {

    @FXML
    private TableView<Bus> table;

    @FXML
    private TextField originField, destinationField, idField;

    @FXML
    private TableColumn<Bus, LocalTime> arrivalCol, departureCol;

    @FXML
    private TableColumn<Bus, LocalDate> dateCol;

    @FXML
    private TableColumn<Bus, String> destinationCol, idCol, originCol;

    @FXML
    private TableColumn<Bus, Status> statusCol;

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
     * arrival columns according to a Time format of 'HH:mm:ss'.
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

    /**
     * Performs filter operation on ObservableList of buses. Will only filter attributes that are provided.
     * If an attribute field is not provided, filter will ignore that field.
     * Will display on TableView buses whose fields match all the corresponding fields ID, Origin, Destination.
     *
     * @param event Source of event: is called when Admin clicks on "Search" button.
     */
    @FXML
    private void submitSearch(ActionEvent event) {
        String busId = idField.getText();
        String origin = originField.getText();
        String destination = destinationField.getText();

        // Filter bus based on non-empty attributes
        ObservableList<Bus> buses = FXCollections.observableArrayList();
        for (Bus bus: Database.getBuses()) {
            if ((busId.isEmpty() || bus.getId().equals(busId))
                    && (origin.isEmpty()|| bus.getOrigin().equalsIgnoreCase(origin))
                    && (destination.isEmpty() || bus.getDestination().equalsIgnoreCase(destination))) {
                buses.add(bus);
            }
        }

        table.setItems(buses);
    }

    /**
     * Handles switching page to corresponding editing page for the selected bus in TableView.
     *
     * @param event Source of event: is called when Admin clicks on "Edit"
     */
    @FXML
    private void editBus(ActionEvent event) {
        try {
            Bus bus = table.getSelectionModel().getSelectedItem();
            Database.setCurrentBus(bus);
            loadFXML("AdminEditBus");
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(e.getMessage());
            alert.setContentText("Please select a bus to edit.");

            alert.showAndWait();
        }
    }

    @FXML
    private void addBus(ActionEvent event) {
        loadFXML("AdminCreateBus");
    }

    /**
     * Handles logic for deleting a bus.
     * Deletes all the bookings that the bus is linked to.
     *
     * @param event Source of event: is called when Admin clicks on "Delete"
     */
    @FXML
    private void deleteBus(ActionEvent event) {
        try {
            Bus bus = table.getSelectionModel().getSelectedItem();
            Database.removeBus(bus);
            String busId = bus.getId();
            Database.getBookings().removeIf(booking -> booking.getBusId().equals(busId));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deleted bus successfully");
            alert.setContentText(String.format("Bus with ID '%s' has been deleted.", busId));

            alert.showAndWait();
            loadFXML("AdminManageBuses");
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(e.getMessage());
            alert.setContentText("Please select a bus to delete.");

            alert.showAndWait();
        }
    }
}

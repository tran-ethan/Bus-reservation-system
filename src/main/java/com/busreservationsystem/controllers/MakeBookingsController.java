package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Bus;
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

public class MakeBookingsController implements Initializable {

    @FXML
    private TableColumn<Bus, LocalTime> arrivalCol;

    @FXML
    private TableColumn<Bus, LocalDate> dateCol;

    @FXML
    private TextField dateField;

    @FXML
    private ChoiceBox<String> dateSortField;

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
    private ChoiceBox<String> priceSortField;

    @FXML
    private TableColumn<Bus, Status> statusCol;

    @FXML
    private Button submit;

    @FXML
    private TableView<Bus> table;

    @FXML
    private TableColumn<Bus, Integer> ticketPriceCol;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<Bus, String>("id"));
        ticketPriceCol.setCellValueFactory(new PropertyValueFactory<Bus, Integer>("ticketPrice"));
        originCol.setCellValueFactory(new PropertyValueFactory<Bus, String>("origin"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<Bus, String>("destination"));
        departureCol.setCellValueFactory(cellData -> cellData.getValue().getDepartureTime());
        departureCol.setCellFactory(new TimeCellFactory());
        arrivalCol.setCellValueFactory(cellData -> cellData.getValue().getArrivalTime());
        arrivalCol.setCellFactory(new TimeCellFactory());
        dateCol.setCellValueFactory(cellData -> cellData.getValue().getDepartureDate());
        dateCol.setCellFactory(new DateCellFactory());
        statusCol.setCellValueFactory(cellData -> cellData.getValue().getStatus());

        Bus exampleBus = new Bus("QWERTY", 13.99, "Montreal", "NYC",
                LocalDate.of(2023, 5, 30),
                LocalTime.of(9, 0), LocalTime.of(12, 0),
                Status.ON_TIME);
        Bus exampleBus2 = new Bus("MORTY", 13.99, "Montreal", "Vancouver",
                LocalDate.of(2023, 5, 29),
                LocalTime.of(8, 0), LocalTime.of(12, 0),
                Status.DELAYED);

        ObservableList<Bus> buses = FXCollections.observableArrayList(exampleBus, exampleBus2);
        table.setItems(buses);
    }

    /**
     * Custom cell factory for the departure column in a JavaFX TableView.
     * Implements the Callback interface to provide a custom TableCell
     * class for displaying LocalTime values in the departure and
     * arrival columns according to a Time format of 'hh:mm:ss'.
     */
    public static class TimeCellFactory implements Callback<TableColumn<Bus, LocalTime>, TableCell<Bus, LocalTime>> {

        @Override
        public TableCell<Bus, LocalTime> call(TableColumn<Bus, LocalTime> col) {
            return new TimeTableCell();
        }

        // Custom TableCell implementation for departure column
        private static class TimeTableCell extends TableCell<Bus, LocalTime> {
            @Override
            protected void updateItem(LocalTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format(item.format(DateTimeFormatter.ofPattern("hh:mm:ss"))));
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
    public static class DateCellFactory implements Callback<TableColumn<Bus, LocalDate>, TableCell<Bus, LocalDate>> {

        @Override
        public TableCell<Bus, LocalDate> call(TableColumn<Bus, LocalDate> col) {
            return new DepartureTableCell();
        }

        private static class DepartureTableCell extends TableCell<Bus, LocalDate> {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format(item.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
                }
            }
        }
    }
    @FXML
    void submitSearch(ActionEvent event) {

    }

}

package com.busreservationsystem.controllers;

import com.busreservationsystem.system.Booking;
import com.busreservationsystem.system.Bus;
import com.busreservationsystem.system.Client;
import com.busreservationsystem.system.Database;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * @author Ethan Tran
 */
public class ClientSeatSelectionController extends ClientController implements Initializable {

    @FXML
    private Label busIdLabel, dateLabel, departureLabel, destinationLabel, priceLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private GridPane seatPane;

    private final ArrayList<String> selectedSeats = new ArrayList<>();

    private final Bus bus = Database.getCurrentBus();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boolean[][] seats = bus.getSeats();
        busIdLabel.setText(bus.getId());
        departureLabel.setText(bus.getOrigin());
        destinationLabel.setText(bus.getDestination());
        dateLabel.setText(bus.getDepartureDateValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        priceLabel.setText(String.format("%.2f$", bus.getTicketPrice()));

        // Display unavailable seats by changing icon and color
        for (Node child: seatPane.getChildren()) {
            MaterialIconView seat = (MaterialIconView) child;
            // the getIndex methods of GridPane returns null if item is at index 0, so convert to back int back to 0
            int col = (GridPane.getColumnIndex(child) == null) ? 0 : GridPane.getColumnIndex(child);
            int row = (GridPane.getRowIndex(child) == null) ? 0 : GridPane.getRowIndex(child);
            if (seats[row][col]) {
                seat.setGlyphName("PERSON");
                seat.setFill(Color.valueOf("#de905080"));
            }
        }
    }

    /**
     * Handles user interaction when Client clicks on a seat.
     * If seat is available, select the seat and show user by changing color, and add to selectedSeats.
     * If seat is unavailable, nothing will happen.
     * If seat is already selected, unselect the seat and remove from selectedSeats.
     *
     * @param event Source of MouseEvent - used to determine which seat has been clicked.
     */
    @FXML
    public void selectSeat(MouseEvent event) {
        // Get seat object
        MaterialIconView seat = (MaterialIconView) event.getSource();
        String color = seat.getFill().toString();

        // Seat with color '0xde924dff' is available. Seat with color '0xde5050ff' is already selected.
        if (color.equals("0xde924dff")) {
            seat.setFill(Color.valueOf("#de5050"));
            selectedSeats.add(seat.getId());
        } else if (color.equals("0xde5050ff")) {
            seat.setFill(Color.valueOf("#de924dff"));
            selectedSeats.remove(seat.getId());
        }

        // Update total price indicator
        totalPriceLabel.setText(String.format("%.2f$", bus.getTicketPrice() * selectedSeats.size()));
    }

    /**
     * Handles logic for a client making a booking.
     * Updates data in bus and creates new booking in the name of Client.
     * Withdraws appropriate amount of money from Client's account to match amount of bookings made.
     *
     * @param event Source of the event - triggered when Client clicks on Book button
     */
    @FXML
    public void makeBooking(ActionEvent event) {
        Client client = Database.getCurrentClient();
        try {
            // Throw an exception if client has not selected any seats
            if (selectedSeats.size() == 0) throw new NullPointerException("No seats selected");

            // Withdraw money from client - possibly throws IllegalArgumentException
            client.withdraw(bus.getTicketPrice() * selectedSeats.size());

            // For every selected seat
            for (String seatNumber : selectedSeats) {
                char letter = seatNumber.charAt(0);
                int row = letter - 'A';
                int col = Character.getNumericValue(seatNumber.charAt(1));
                // Update seats in bus
                bus.getSeats()[row][col - 1] = true;
                // Make booking in name of Client
                Booking booking = new Booking(bus, client, letter, col);
                Database.addBooking(booking);
            }

            // Display success
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful booking");
            alert.setContentText("Booked seats " + selectedSeats);
            alert.showAndWait();

            // Get user to bookings page
            loadFXML("ClientMakeBookings");
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(e.getMessage());
            alert.setContentText(String.format("""
                    You do not have enough money to book these seats. Please deposit more money.
                    You currently have %.2f
                    This transaction costs %.2f""",
                    client.getBalance(), bus.getTicketPrice() * selectedSeats.size()));
            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(e.getMessage());
            alert.setContentText("Please select seats to book.");
            alert.showAndWait();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        loadFXML("ClientMakeBookings");
    }
}

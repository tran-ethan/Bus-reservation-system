package com.busreservationsystem.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking implements Comparable<Booking> {

    private String busId;
    private String origin;
    private String destination;
    private double price;
    private final ObjectProperty<LocalTime> departureTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> departureDate = new SimpleObjectProperty<>();
    private String clientUsername;
    private String clientName;
    private char row;
    private int column;

    public Booking(Bus bus, Client client, Seat seat) {
        this.busId = bus.getId();
        this.origin = bus.getOrigin();
        this.destination = bus.getDestination();
        this.price = bus.getTicketPrice();
        this.departureDate.set(bus.getDepartureDateValue());
        this.departureTime.set(bus.getArrivalTimeValue());
        this.clientUsername = client.getUsername();
        this.clientName = client.getFullName();
        this.row = seat.getRow();
        this.column = seat.getColumn();
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ObjectProperty<LocalTime> getDepartureTime() {
        return departureTime;
    }

    @JsonProperty("departureTime")
    public LocalTime getDepartureTimeValue() {
        return departureTime.get();
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime.set(departureTime);
    }

    public ObjectProperty<LocalDate> getDepartureDate() {
        return departureDate;
    }

    @JsonProperty("departureDate")
    public LocalDate getDepartureDateValue() {
        return departureDate.get();
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate.set(departureDate);
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(Booking o) {
        return 0;
    }
}

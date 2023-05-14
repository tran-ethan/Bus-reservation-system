package com.busreservationsystem.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 * The Bus class represents a single Bus that can travel to and from a location.
 * Every bus has a unique identifier Bus ID, and a 2D boolean array containing information regarding the seats.
 *
 * @author Ethan Tran
 * @author Christopher Soussa
 */
public class Bus {

    private String id;
    private double ticketPrice;
    private String origin;
    private String destination;
    private final ObjectProperty<Status> status = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> departureDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> arrivalTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> departureTime = new SimpleObjectProperty<>();

    private boolean[][] seats;

    public Bus() {}

    public Bus(String id, double ticketPrice, String origin, String destination, LocalDate departureDate, LocalTime departureTime, LocalTime arrivalTime, Status status) {
        this(id, ticketPrice, origin, destination, departureDate, departureTime, arrivalTime, status, new boolean[10][4]);
    }

    public Bus(String id, double ticketPrice, String origin, String destination, LocalDate departureDate, LocalTime departureTime, LocalTime arrivalTime, Status status, boolean[][] seats) throws IllegalArgumentException {
        setId(id);
        setTicketPrice(ticketPrice);
        setOrigin(origin);
        setDestination(destination);
        this.status.set(status);
        this.departureDate.set(departureDate);
        this.departureTime.set(departureTime);
        this.arrivalTime.set(arrivalTime);
        this.seats = seats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws IllegalArgumentException{
        if (id.isEmpty()) throw new IllegalArgumentException("ID cannot be empty");
        for (Bus bus: Database.getBuses()) {
            if (bus.getId().equals(id)) throw new IllegalArgumentException("Bus ID already exists");
        }
        this.id = id;
    }

    public void setTicketPrice(double ticketPrice) throws IllegalArgumentException {
        if (ticketPrice <= 0) throw new IllegalArgumentException("Invalid ticket price");
        this.ticketPrice = ticketPrice;
    }

    public void setOrigin(String origin) throws IllegalArgumentException {
        if (origin.isEmpty()) throw new IllegalArgumentException("Origin cannot be empty");
        this.origin = origin;
    }

    public void setDestination(String destination) throws IllegalArgumentException {
        if (destination.isEmpty()) throw new IllegalArgumentException("Destination cannot be empty");
        this.destination = destination;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public ObjectProperty<Status> getStatus() {
        return status;
    }

    @JsonProperty("status")
    public Status getStatusValue() {
        return status.get();
    }

    public void setStatus(Status status) {
        this.status.set(status);
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

    public ObjectProperty<LocalTime> getArrivalTime() {
        return arrivalTime;
    }

    @JsonProperty("arrivalTime")
    public LocalTime getArrivalTimeValue() {
        return arrivalTime.get();
    }
    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime.set(arrivalTime);
    }

    public void setSeats(boolean[][] seats) {
        this.seats = seats;
    }

    public boolean[][] getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id='" + id + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", status=" + getStatusValue() +
                ", departureDate=" + getDepartureDate() +
                ", arrivalTime=" + getArrivalTimeValue() +
                ", departureTime=" + getDepartureTimeValue() +
                '}';
    }
}

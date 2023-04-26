package com.busreservationsystem.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class Bus implements Comparable<Bus> {

    private String id;
    private double ticketPrice;
    private String origin;
    private String destination;
    private final ObjectProperty<Status> status = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> departureDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> arrivalTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> departureTime = new SimpleObjectProperty<>();

    public Bus() {

    }
    public Bus(String id, double ticketPrice, String origin, String destination, LocalDate departureDate, LocalTime departureTime, LocalTime arrivalTime, Status status) {
        this.id = id;
        this.ticketPrice = ticketPrice;
        this.origin = origin;
        this.destination = destination;
        this.status.set(status);
        this.departureDate.set(departureDate);
        this.departureTime.set(departureTime);
        this.arrivalTime.set(arrivalTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
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

    @Override
    public int compareTo(Bus o) {
        return id.compareTo(o.getId());
    }
}

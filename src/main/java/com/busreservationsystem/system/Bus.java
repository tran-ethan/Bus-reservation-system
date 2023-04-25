package com.busreservationsystem.system;

import java.time.Duration;
import java.time.LocalDateTime;

public class Bus {

    private static int nextId = 1;

    private String id;
    private double ticketPrice;
    private String source;
    private String destination;
    private Status status;

    private Duration duration;

    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;

    public Bus(double ticketPrice, String source, String destination, Status status, Duration duration, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        this.id = String.format("B%04d", nextId++);
        this.ticketPrice = ticketPrice;
        this.source = source;
        this.destination = destination;
        this.status = status;
        this.duration = duration;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
    }
}

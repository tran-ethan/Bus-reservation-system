package com.busreservationsystem.system;

public enum Status {
    DELAYED,
    ON_TIME,
    ON_ROUTE,
    CANCELLED;

    public static Status fromString(String statusValue) {
        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(statusValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + statusValue);
    }
}

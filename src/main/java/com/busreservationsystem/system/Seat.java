package com.busreservationsystem.system;

public class Seat {

    private char row;
    private int column;
    private boolean isBooked;

    public Seat(char row, int column) {
        this(row, column, false);
    }

    public Seat(char row, int column, boolean isBooked) {
        this.row = row;
        this.column = column;
        this.isBooked = isBooked;
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

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}

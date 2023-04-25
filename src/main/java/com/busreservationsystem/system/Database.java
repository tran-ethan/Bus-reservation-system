package com.busreservationsystem.system;

import java.util.HashSet;

/**
 * The Database class embodies a database system to keep track of all
 * Users, Admins, Buses, and Bookings.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyhronopoulos
 * @author Christopher Soussa
 */

public class Database {

    private static final HashSet<User> users = new HashSet<>();
    private static final HashSet<Bus> buses = new HashSet<>();
    private static final HashSet<Booking> bookings = new HashSet<>();

    /**
     * Constructor for the Database.
     * Fills all according HashSets according to JSON files specified.
     * Parameters specified should contain only the basename of the JSON files
     * stored at the root level of a directory called 'database'.
     *
     * @param customerJSON File name of customers JSON file.
     * @param adminJSON File name of admins JSON file.
     * @param bookingJSON File name of bookings JSON file.
     * @param busJSON File name of buses JSON file.
     */

    public Database(String customerJSON, String adminJSON, String bookingJSON, String busJSON) {
        System.out.println("do stuff");
    }
}

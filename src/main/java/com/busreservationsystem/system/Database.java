package com.busreservationsystem.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The Database class embodies a database system to keep track of all
 * Users, Admins, Buses, and Bookings.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyhronopoulos
 * @author Christopher Soussa
 */
public class Database {

    private final static HashSet<User> users = new HashSet<>();
    private final static TreeSet<Bus> buses = new TreeSet<>();
    private final static HashSet<Booking> bookings = new HashSet<>();

    /**
     * Constructor for the Database.
     * Fills all according HashSets according to JSON files specified.
     * Parameters specified should contain only the basename of the JSON files
     * stored at the root level of a directory called 'database'.
     *
     * @param customerJSON File name of customers JSON file.
     * @param adminJSON File name of admins JSON file.
     * @param bookingsJSON File name of bookings JSON file.
     * @param busJSON File name of buses JSON file.
     */

    public Database(String customerJSON, String adminJSON, String bookingsJSON, String busJSON) {
        loadJson(busJSON, buses);
    }

    public void loadJson(String json, Set<?> data) {
        json = String.format("src/main/resources/com/busreservationsystem/database/%s.json", json);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        File jsonFile = new File(json);
        try {
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(TreeSet.class, Bus.class);
            data.addAll(objectMapper.readValue(jsonFile, collectionType));
            for (Bus bus: buses) {
                System.out.println(bus.toString());
                System.out.println(bus.getArrivalTimeValue());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TreeSet<Bus> getBuses() {
        return buses;
    }
}

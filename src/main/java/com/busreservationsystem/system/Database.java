package com.busreservationsystem.system;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * The Database class embodies a database system to keep track of all
 * Users, Admins, Buses, and Bookings.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyhronopoulos
 * @author Christopher Soussa
 */
public class Database {

    private final static HashSet<User> clients = new HashSet<>();
    private final static TreeSet<Bus> buses = new TreeSet<>();
    private final static TreeSet<Booking> bookings = new TreeSet<>();

    /**
     * Constructor for the Database.
     * Fills all according Sets according to JSON files specified.
     * Parameters specified should contain only the basename of the JSON files
     * stored at the root level of a directory called 'database'.
     *
     * @param clientsJSON File name of customers JSON file.
     * @param adminJSON File name of admins JSON file.
     * @param bookingsJSON File name of bookings JSON file.
     * @param busJSON File name of buses JSON file.
     */

    public Database(String clientsJSON, String adminJSON, String bookingsJSON, String busJSON) {
        loadJson(busJSON, buses, Bus.class);
        loadJson(clientsJSON, clients, Client.class);

        // loadJson(bookingsJSON, bookings);
//        Client ethan = new Client("Ethan Tran", "ethan312", "pass", "ethantran@yahoo.ca");
//        Client niko = new Client("Nikolaos Polyhronopoulos", "niko123", "nikopass", "nikopolyhron@gmail.com");
//        Client chris = new Client("Christopher Soussa", "chris321", "password", "chrissoussa@yahoo.com");
//        addUser(ethan);
//        addUser(niko);
//        addUser(chris);
//        writeJson(clientsJSON, users);
//        Seat seat = new Seat('A', 5);
//        Seat seat2 = new Seat('B', 2);
//        addBooking(new Booking(buses.first(), ethan, seat));
//        addBooking(new Booking(buses.last(), ethan, seat2));
//        addBooking(new Booking(buses.last(), niko, seat));
//        writeJson(bookingsJSON, bookings);
    }

    public void loadJson(String json, Set<?> data, Class<?> classType) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(Set.class, classType);

        json = String.format("src/main/resources/com/busreservationsystem/database/%s.json", json);
        File jsonFile = new File(json);

        try {
            data.addAll(objectMapper.readValue(jsonFile,  collectionType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeJson(String json, Set<?> data) {
        json = String.format("src/main/resources/com/busreservationsystem/database/%s.json", json);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        File jsonFile = new File(json);
        try {
            objectMapper.writeValue(jsonFile, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addClient(User user) {
        clients.add(user);
    }
    public static void addBus(Bus bus) {
        buses.add(bus);
    }

    public static void addBooking(Booking booking) {
        bookings.add(booking);
    }
    public static TreeSet<Bus> getBuses() {
        return buses;
    }
}

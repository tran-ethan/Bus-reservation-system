package com.busreservationsystem.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * The Database class embodies a database system to keep track of all
 * Users, Admins, Buses, and Bookings.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyhronopoulos
 * @author Christopher Soussa
 */
public class Database {

    private final static ArrayList<Client> clients = new ArrayList<>();
    private final static ArrayList<Admin> admins = new ArrayList<>();
    private final static ArrayList<Bus> buses = new ArrayList<>();
    private final static ArrayList<Booking> bookings = new ArrayList<>();
    private static Admin currentAdmin = null;
    private static Client currentClient = null;
    private static Bus currentBus = null;
    private static Booking currentBooking = null;

    /**
     * Constructor for the Database.
     * Fills all according Lists according to JSON files specified.
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
        loadJson(bookingsJSON, bookings, Booking.class);
        loadJson(adminJSON, admins, Admin.class);

        // Temp bookings test
//        Bus bus = new Bus("ABC10", 100, "Montreal", "NYC", LocalDate.of(2023, 5, 6),
//                LocalTime.of(10, 0),
//                LocalTime.of(15, 0),
//                Status.ON_TIME, new boolean[10][4]);
//
//        Bus bus1 = new Bus("1", 20.0, "City A", "City B",
//                LocalDate.of(2023, 5, 6),
//                LocalTime.of(10, 0),
//                LocalTime.of(14, 0),
//                Status.ON_TIME, new boolean[10][4]);
//
//        Bus bus2 = new Bus("2", 15.0, "City C", "City D",
//                LocalDate.of(2023, 5, 7),
//                LocalTime.of(9, 0),
//                LocalTime.of(13, 0),
//                Status.DELAYED, new boolean[10][4]);
//
//        // Create an ArrayList of buses
//        ArrayList<Bus> buses = new ArrayList<>();
//        buses.add(bus);
//        buses.add(bus1);
//        buses.add(bus2);
//
//        writeJson("tmp", buses);
    }

    /**
     * Loads the data contained in the JSON file into a given List of data.
     *
     * @param json The name of the JSON file to write the data into.
     * @param data The List of data to write into the JSON file.
     * @param classType The class type of the objects contained in the List.
     */
    public void loadJson(String json, List<?> data, Class<?> classType) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, classType);

        json = String.format("src/main/resources/com/busreservationsystem/database/%s.json", json);
        File jsonFile = new File(json);

        try {
            data.addAll(objectMapper.readValue(jsonFile,  collectionType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes the data from the List into the given JSON file.
     *
     * @param json The name of the JSON file to write the data into.
     * @param data The List of data to write into the JSON file.
     */
    public void writeJson(String json, List<?> data) {
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

    public static void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }

    public static void setCurrentClient(Client client) {
        if (client == null) throw new NullPointerException("Client not selected");
        currentClient = client;
    }

    public static void setCurrentBooking(Booking booking) {
        if (booking == null) throw new NullPointerException("Booking not selected");
        currentBooking = booking;
    }

    public static void setCurrentBus(Bus bus) throws NullPointerException {
        if (bus == null) throw new NullPointerException("Bus not selected");
        Database.currentBus = bus;
    }

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static Client getCurrentClient() {
        return currentClient;
    }

    public static Bus getCurrentBus() {
        return currentBus;
    }

    public static Booking getCurrentBooking() {
        return currentBooking;
    }

    public static void addClient(Client client) {
        clients.add(client);
    }

    public static void addBus(Bus bus) {
        buses.add(bus);
    }

    public static void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public static Bus getBusFromId(String busId) throws NoSuchElementException {
        for (Bus bus: buses) {
            if (bus.getId().equals(busId)) {
                return bus;
            }
        }
        throw new NoSuchElementException("No bus with ID: " + busId);
    }

    public static Client getClientFromUsername(String username) throws NoSuchElementException {
        for (Client client: clients) {
            if (client.getUsername().equals(username)) {
                return client;
            }
        }
        throw new NoSuchElementException("No client with username: " + username);
    }



    public static ArrayList<Client> getClients() {
        return clients;
    }

    public static ArrayList<Admin> getAdmins() {
        return admins;
    }

    public static ArrayList<Bus> getBuses() {
        return buses;
    }

    public static List<Booking> getCurrentClientBookings() {
        return bookings.stream()
                .filter(booking -> booking.getClientUsername().equals(currentClient.getUsername()))
                .collect(Collectors.toList());
    }

    public static List<Booking> getBookings() {
        return bookings;
    }

    public static void removeBooking(Booking booking) throws NullPointerException {
        if (booking == null) throw new NullPointerException("Booking not selected");
        bookings.remove(booking);
    }

    public static void removeClient(Client client) {
        if (client == null) throw new NullPointerException("Client not selected");
        clients.remove(client);
    }
}

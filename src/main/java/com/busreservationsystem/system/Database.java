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
 * Everything in this class is public and static in order for all controllers to be able to access fields
 * and variables without instantiating Database, as all controllers will point to the same one.
 *
 * @author Ethan Tran
 */
public class Database {

    private final static ArrayList<Client> clients = new ArrayList<>();
    private final static ArrayList<Admin> admins = new ArrayList<>();
    private final static ArrayList<Bus> buses = new ArrayList<>();
    private final static ArrayList<Booking> bookings = new ArrayList<>();

    private static Admin currentAdmin;
    private static Client currentClient;
    private static Bus currentBus;
    private static Booking currentBooking;

    private static String clientsJSON;
    private static String adminJSON;
    private static String bookingsJSON;
    private static String busJSON;


    /**
     * Constructor for the Database.
     * Sets all according data according to JSON files specified.
     * Parameters specified should contain only the basename of the JSON files
     * stored at the root level of a directory called 'database'.
     *
     * @param clientsJSON File name of customers JSON file.
     * @param adminJSON File name of admins JSON file.
     * @param bookingsJSON File name of bookings JSON file.
     * @param busJSON File name of buses JSON file.
     */
    public Database(String clientsJSON, String adminJSON, String bookingsJSON, String busJSON) {
        Database.clientsJSON = clientsJSON;
        Database.adminJSON = adminJSON;
        Database.bookingsJSON = bookingsJSON;
        Database.busJSON = busJSON;
    }

    /**
     * This method will be called when the user first launches the application.
     */
    public static void loadJsons() {
        loadJson(clientsJSON, clients, Client.class);
        loadJson(adminJSON, admins, Admin.class);
        loadJson(bookingsJSON, bookings, Booking.class);
        loadJson(busJSON, buses, Bus.class);
    }

    /**
     * This method will be called when the user closes the application.
     * It will update all the JSON files to match all the interactions that happened during the session.
     */
    public static void writeJsons() {
        writeJson(clientsJSON, clients);
        writeJson(adminJSON, admins);
        writeJson(bookingsJSON, bookings);
        writeJson(busJSON, buses);
    }

    /**
     * Loads the data contained in the JSON file into a given List of data.
     *
     * @param json The name of the JSON file to write the data into.
     * @param data The List of data to write into the JSON file.
     * @param classType The class type of the objects contained in the List.
     */
    public static void loadJson(String json, List<?> data, Class<?> classType) {
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
    public static void writeJson(String json, List<?> data) {
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

    public static void addAdmin(Admin admin) {
        admins.add(admin);
    }

    public static void addBus(Bus bus) {
        buses.add(bus);
    }

    public static void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * @param busId The bus ID of the bus to look for
     * @return A bus object with the matching ID
     * @throws NoSuchElementException if no bus with that ID is found
     */
    public static Bus getBusFromId(String busId) throws NoSuchElementException {
        for (Bus bus: buses) {
            if (bus.getId().equals(busId)) {
                return bus;
            }
        }
        throw new NoSuchElementException("No bus with ID: " + busId);
    }

    /**
     * @param username Username of the client
     * @return The client object with that specific username
     * @throws NoSuchElementException if no clients with that username are found
     */
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

    /**
     * Filters out bookings that do not belong to the client, such that client will only be able
     * to view bookings that belong to them.
     *
     * @return A List of bookings belonging to the current client
     */
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

    public static void removeBus(Bus bus) {
        if (bus == null) throw new NullPointerException("Bus not selected");
        buses.remove(bus);
    }
}

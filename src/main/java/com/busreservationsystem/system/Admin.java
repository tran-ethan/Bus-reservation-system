package com.busreservationsystem.system;

/**
 * The Admin class represents an administrator in bus reservation system
 * with the ability to manage customers and flights.
 * Is subclass of User.
 *
 * @author Nikolaos Polyhronopoulos
 */

public class Admin extends User {

    public Admin() {}

    public Admin(String fullName, String username, String password, String email) {
        super(fullName, username, password, email);
    }

    @Override
    public String getType() {
        return "Administrator";
    }
}

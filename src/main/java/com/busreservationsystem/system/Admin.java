package com.busreservationsystem.system;

/**
 * The Admin class represents an administrator in bus reservation system
 * with the ability to manage customers and flights.
 * Is subclass of User.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyhronopoulos
 * @author Christopher Soussa
 */

public class Admin extends User {

    public Admin(String fullName, String username, String password, String email) {
        super(fullName, username, password, email);
    }

    public String getType() {
        return "Administrator";
    }
}

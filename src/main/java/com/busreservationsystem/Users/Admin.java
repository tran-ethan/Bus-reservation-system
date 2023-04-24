package com.busreservationsystem.Users;

/**
 * The Admin class represents an administrator in bus reservation system
 * with the ability to manage customers.
 * Is subclass of User.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyhronopoulos
 * @author Christopher Soussa
 */

public class Admin extends User {

    public Admin(String firstName, String lastName, String username, String password, String email) {
        super(firstName, lastName, username, password, email);
    }

    public String getType() {
        return "Administrator";
    }
}
